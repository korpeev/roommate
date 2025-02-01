package org.broxton.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.broxton.auth.dto.RefreshTokenRequestDto;
import org.broxton.auth.dto.TokensGeneratedDto;
import org.broxton.exceptions.TokenInvalidException;
import org.broxton.user.dto.*;
import org.broxton.user.entity.UserEntity;
import org.broxton.user.service.UserService;
import org.broxton.utils.JwtUtil;
import org.broxton.utils.TokenType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtUtil jwtUtil;
  private final UserService userService;

  @Transactional
  public ResponseEntity<UserAuthDto> register(UserSignUpRequestDto dto) {
    UserEntity user = userService.createUser(dto);

    TokensGeneratedDto userTokens = generateUserTokens(user);
    userService.updateUserRefreshToken(user.getId(), userTokens.getRefreshToken());

    UserAuthDto userAuthDto = UserAuthDto.builder()
            .tokens(userTokens)
            .user(UserDto.builder()
                    .email(user.getEmail())
                    .isBanned(user.getIsBanned())
                    .username(user.getUsername())
                    .build())
            .build();

    return new ResponseEntity<>(userAuthDto, HttpStatus.CREATED);
  }

  @Transactional
  public ResponseEntity<UserAuthDto> authenticate(UserSiginRequestDto dto) {
    UserEntity user = userService.validateAndGetUser(dto);

    TokensGeneratedDto userTokens = generateUserTokens(user);
    user.setRefreshToken(userTokens.getRefreshToken());

    userService.updateUserRefreshToken(user.getId(), userTokens.getRefreshToken());

    UserDto userDto = UserDto.builder()
            .email(user.getEmail())
            .username(user.getUsername())
            .isBanned(user.getIsBanned())
            .build();

    UserAuthDto userAuthDto = UserAuthDto.builder()
            .user(userDto)
            .tokens(userTokens)
            .build();


    return new ResponseEntity<>(userAuthDto, HttpStatus.OK);
  }

  public ResponseEntity<TokensGeneratedDto> refreshTokens(RefreshTokenRequestDto dto) {

    if (jwtUtil.isTokenExpired(dto.getRefreshToken(), TokenType.REFRESH)) {
      throw new TokenInvalidException("Token expired");
    }

    String email = jwtUtil.getEmail(dto.getRefreshToken(), TokenType.REFRESH);
    UserEntity user = userService.findUserByEmail(email);

    boolean tokenIsValid = user.getRefreshToken().equals(dto.getRefreshToken())
            && jwtUtil.validateToken(dto.getRefreshToken(), user.getEmail(), TokenType.REFRESH);

    if (!tokenIsValid) {
      throw new TokenInvalidException("Token invalid");
    }

    TokensGeneratedDto userTokens = generateUserTokens(user);

    userService.updateUserRefreshToken(user.getId(), userTokens.getRefreshToken());

    return new ResponseEntity<>(userTokens, HttpStatus.OK);
  }

  private TokensGeneratedDto generateUserTokens(UserEntity user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", user.getId());
    claims.put("roles", user.getRoles());

    String accessToken = jwtUtil.generateAccessToken(user.getEmail(), claims);
    String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), claims);

    return TokensGeneratedDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }
}

package org.broxton.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.broxton.auth.dto.RefreshTokenRequestDto;
import org.broxton.auth.dto.TokensGeneratedDto;
import org.broxton.auth.mapper.AuthMapper;
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
  private final AuthMapper authMapper;

  @Transactional
  public ResponseEntity<UserAuthDto> register(UserSignUpRequestDto dto) {
    UserEntity user = userService.createUser(dto);
    return new ResponseEntity<>(makeUserAuthResponse(user), HttpStatus.CREATED);
  }

  @Transactional
  public ResponseEntity<UserAuthDto> authenticate(UserSiginRequestDto dto) {
    UserEntity user = userService.validateAndGetUser(dto);
    return new ResponseEntity<>(makeUserAuthResponse(user), HttpStatus.OK);
  }

  public ResponseEntity<TokensGeneratedDto> refreshTokens(RefreshTokenRequestDto dto) {

    if (jwtUtil.isTokenExpired(dto.refreshToken(), TokenType.REFRESH)) {
      throw new TokenInvalidException("Token expired");
    }

    String email = jwtUtil.getEmail(dto.refreshToken(), TokenType.REFRESH);
    UserEntity user = userService.findUserByEmail(email);

    boolean tokenIsValid = user.getRefreshToken().equals(dto.refreshToken())
            && jwtUtil.validateToken(dto.refreshToken(), user.getEmail(), TokenType.REFRESH);

    if (!tokenIsValid) {
      throw new TokenInvalidException("Token invalid");
    }

    TokensGeneratedDto userTokens = generateUserTokens(user);

    userService.updateUserRefreshToken(user.getId(), userTokens.getRefreshToken());

    return new ResponseEntity<>(userTokens, HttpStatus.OK);
  }

  private UserAuthDto makeUserAuthResponse(UserEntity user) {
    TokensGeneratedDto userTokens = generateUserTokens(user);
    userService.updateUserRefreshToken(user.getId(), userTokens.getRefreshToken());
    return authMapper.toUserAuthDto(user, userTokens);
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

package org.broxton.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.broxton.auth.dto.RefreshTokenRequestDto;
import org.broxton.auth.dto.TokensGeneratedDto;
import org.broxton.user.dto.UserAuthDto;
import org.broxton.user.dto.UserSiginRequestDto;
import org.broxton.user.dto.UserSignUpRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<UserAuthDto> register(@RequestBody UserSignUpRequestDto dto) {
    return authService.register(dto);
  }

  @PostMapping("/login")
  public ResponseEntity<UserAuthDto> login(@RequestBody UserSiginRequestDto dto) {
    return authService.authenticate(dto);
  }

  @PostMapping("/refresh")
  public ResponseEntity<TokensGeneratedDto> refresh(@RequestBody RefreshTokenRequestDto dto) {
    return authService.refreshTokens(dto);
  }
}

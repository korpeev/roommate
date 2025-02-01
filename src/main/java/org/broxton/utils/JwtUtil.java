package org.broxton.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.broxton.auth.dto.TokensGeneratedDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  @Value("${ACCESS_SECRET}")
  private String ACCESS_SECRET;

  @Value("${REFRESH_SECRET}")
  private String REFRESH_SECRET;

  private final long ACCESS_EXPIRATION_TIME = 86400000; // 24hour
  private final long REFRESH_EXPIRATION_TIME = ACCESS_EXPIRATION_TIME * 30; // 30 days

  public String generateAccessToken(String email,  Map<String, Object> claims) {
    return generateTokenWithCustomClaims(
            email, claims,
            getSigningKey(getSecretKey(TokenType.ACCESS)),
            ACCESS_EXPIRATION_TIME
    );
  }

  public String generateRefreshToken(String email, Map<String, Object> claims) {
    return generateTokenWithCustomClaims(
            email, claims,
            getSigningKey(getSecretKey(TokenType.REFRESH)),
            REFRESH_EXPIRATION_TIME
    );
  }

  public Claims extractAllClaims(String token, TokenType tokenType) {
    return Jwts.parser()
            .verifyWith(getSigningKey(getSecretKey(tokenType)))
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  public String getEmail(String token, TokenType tokenType) {
    return extractAllClaims(token, tokenType).getSubject();
  }

  public Boolean validateToken(String token, String email, TokenType tokenType) {
    String subject = extractAllClaims(token, tokenType).getSubject();
    return (subject.equals(email) && !isTokenExpired(token, tokenType));
  }

  private String generateTokenWithCustomClaims(String email, Map<String, Object> claims, Key key, Long exp) {
    return Jwts.builder()
            .subject(email)
            .claims(claims)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + exp))
            .signWith(key)
            .compact();
  }

  private String getSecretKey(TokenType tokenType) {
    return tokenType == TokenType.ACCESS ? ACCESS_SECRET : REFRESH_SECRET;
  }

  public Boolean isTokenExpired(String token, TokenType tokenType) {
    Date expiration = extractAllClaims(token, tokenType).getExpiration();
    return expiration.before(new Date());
  }

  private SecretKey getSigningKey(String jwtSecret) {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

package org.example.backend.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * JWT工具类
 */
@Component
public class JwtUtil {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  // 定义密钥和过期时间，密钥需要更长一些以满足 HS256 要求
  private String secret = "DoctorChildLongerSecretKey1234567890"; //
  private long expirationTime = 1000 * 60 * 60 * 5;

  private Key getSigningKey() {
    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
  }

  // 生成JWT令牌
  public String generateToken(String userId) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userId);

    try {
      logger.info("Generating token for userId: {}", userId); // 日志记录
      String token = Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
          .signWith(getSigningKey(), SignatureAlgorithm.HS256)
          .compact();
      logger.info("Generated Token: {}", token); // 打印生成的 token
      return token;
    } catch (Exception e) {
      logger.error("Token generation failed", e); // 打印错误日志
      throw new RuntimeException("Token generation failed", e);
    }
  }

  // 验证JWT令牌
  public boolean validateToken(String token) {
    try {
      logger.info("Validating token: {}", token);
      Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      logger.error("Invalid token", e); // 打印错误日志
      return false;
    }
  }
  // 从JWT令牌中获取userId
  public String extractUserId(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.get("userId", String.class);
  }
}

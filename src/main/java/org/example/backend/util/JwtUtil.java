package org.example.backend.util;

import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *JWT工具类
 * @author  Q
 */
@Component
public class JwtUtil {

  // 定义密钥和过期时间
  private String secret = "yourSecretKey";
  private long expirationTime = 1000 * 60 * 60 * 24; // 1 day

  // 生成JWT令牌
  public String generateToken(String userId) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userId);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  // 验证JWT令牌
  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false; // Token 失效或非法
    }
  }

  // 从JWT令牌中获取userId
  public String extractUserId(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
    return claims.get("userId", String.class);
  }
}


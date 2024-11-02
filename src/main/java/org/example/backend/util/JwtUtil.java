package org.example.backend.util;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

/**
 *JWT工具类
 * @author  Q
 */
@Component
public class JwtUtil {

  private String secretKey = "DoctorChild";

  public String generateToken(String userId) {
    return Jwts.builder()
        .setSubject(userId)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000)) // 2个小时过期
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public Claims validateToken(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();
  }
}


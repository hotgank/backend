package org.example.backend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.util.JwtUtil;
import org.example.backend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

/**
 * JWT 认证过滤器
 * @author Q
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private RedisUtil redisUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil, RedisUtil redisUtil) {
    this.jwtUtil = jwtUtil;
    this.redisUtil = redisUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String token = authorizationHeader.substring(7);

      // 从请求头中获取 token 后，先解析 token 获取 userId
      String userIdFromToken = jwtUtil.extractUserId(token);
      //System.out.println("userIdFromToken: " + userIdFromToken);

      // 从 Redis 中获取对应的 token
      String tokenFromRedis = redisUtil.getTokenFromRedis(userIdFromToken);
      System.out.println("tokenFromRedis: " + tokenFromRedis);

      // 比较 Redis 中存储的 token 和请求中的 token 是否一致
      if (tokenFromRedis != null && tokenFromRedis.equals(token) && jwtUtil.validateToken(token)) {
        // 创建一个简单的 Authentication 对象，只包含 userId
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userIdFromToken, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 设置 userId 到请求上下文中（如果需要使用）
        request.setAttribute("userId", userIdFromToken);
        //System.out.println("Token matched and valid, user authenticated with userId: " + userIdFromToken);
      } else {
        // 如果 token 不匹配或者无效，返回 401 Unauthorized 错误
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }
}


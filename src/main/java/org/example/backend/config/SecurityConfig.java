package org.example.backend.config;

import org.example.backend.filter.JwtAuthenticationFilter;
import org.example.backend.util.JwtUtil;
import org.example.backend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired private JwtUtil jwtUtil;

  @Autowired private RedisUtil redisUtil;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable() // 禁用 CSRF 防护
        .cors()
        .and() // 启用 CORS 支持
        .authorizeHttpRequests(
            (requests) ->
                requests
                    .requestMatchers("/api/userLogin/**")
                    .permitAll() // 允许登录相关的请求
                    .requestMatchers("/api/DoctorLogin/**")
                    .permitAll()
                    .requestMatchers("/api/AdminLogin/**")
                    .permitAll()
                    .requestMatchers("/api/DoctorRegister/**")
                    .permitAll()
                    .requestMatchers("/UserAvatar/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated() // 其他请求都需要认证
            )
        .addFilterBefore(
            new JwtAuthenticationFilter(jwtUtil, redisUtil),
            UsernamePasswordAuthenticationFilter.class) // 添加自定义的 JWT 过滤器
        .formLogin()
        .disable() // 禁用表单登录
        .httpBasic()
        .disable(); // 禁用 HTTP 基本认证

    return http.build();
  }
}

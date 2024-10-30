package org.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()  // 禁用 CSRF 防护
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/**").permitAll()  // 放行指定的路径
            .anyRequest().authenticated()  // 其他路径需要认证
        )
        .formLogin().disable()  // 禁用表单登录
        .httpBasic().disable(); // 禁用 HTTP 基本认证

    return http.build();
  }
}

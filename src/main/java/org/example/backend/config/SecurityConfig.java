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

/**
 * import org.example.backend.filter.JwtAuthenticationFilter;
 * import org.example.backend.util.JwtUtil;
 * import org.example.backend.util.RedisUtil;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.web.SecurityFilterChain;
 * import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 *
 * @Configuration
 * public class SecurityConfig {
 *
 *     @Autowired
 *     private JwtUtil jwtUtil;
 *
 *     @Autowired
 *     private RedisUtil redisUtil;
 *
 *     @Bean
 *     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 *         http
 *             .csrf().disable()
 *             .authorizeRequests()
 *             .antMatchers("/api/userLogin/**").permitAll() // 允许登录相关的请求
 *             .anyRequest().authenticated()  // 其他请求都需要认证
 *             .and()
 *             .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, redisUtil), UsernamePasswordAuthenticationFilter.class);
 *
 *         return http.build();
 *     }
 * }
 */

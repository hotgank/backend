package org.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    // 创建CorsConfiguration对象
    CorsConfiguration config = new CorsConfiguration();

    // 设置允许的域名
    config.addAllowedOrigin("http://*:5173");
    config.addAllowedOrigin("http://*:10689");
    config.addAllowedOriginPattern("*");
    // 是否允许携带Cookie
    config.setAllowCredentials(true);

    // 允许的请求方法
    config.addAllowedMethod("*"); // GET, POST, PUT, DELETE等

    // 允许的请求头
    config.addAllowedHeader("*");

    // 基于URL的CORS配置
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsFilter(source);
  }
}

package org.example.backend.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

/**
 * Redis配置
 *
 * @author q
 */
@Configuration
@EnableCaching
public class RedisConfig {

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    // Redis 配置
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    configuration.setHostName("1.95.130.39");
    configuration.setPort(6379);
    configuration.setUsername("Redis666"); // 如果 Redis 配置了用户名
    configuration.setPassword("myRedis"); // Redis 密码

    // 创建 LettuceConnectionFactory
    return new LettuceConnectionFactory(configuration);
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    return template;
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(factory);
    return template;
  }

  @Bean
  public RedisTemplate<String, Integer> integerRedisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Integer> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    return template;
  }
}

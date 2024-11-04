package org.example.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 * @author Q
 */
@Component
public class RedisUtil {
  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  public void storeTokenInRedis(String userId, String token) {
    redisTemplate.opsForValue().set(userId, token, 300, TimeUnit.MINUTES); // 5小时
  }

  public boolean validateToken(String userId, String token) {
    // 从 Redis 获取存储的 token
    String storedToken = redisTemplate.opsForValue().get(userId);
    return storedToken != null && storedToken.equals(token);
  }
}

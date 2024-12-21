package org.example.backend.service.serviceImpl.admin;

import jakarta.annotation.Resource;
import org.example.backend.mapper.admin.AdminMapper;
import org.example.backend.service.admin.AdminManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AdminManageServiceImpl implements AdminManageService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired AdminMapper adminMapper;
    @Override
    public String generateRegisterCode(String email) {
        // 检查Redis中是否已存在该邮箱的验证码
        if (Boolean.TRUE.equals(redisTemplate.hasKey(email))) {
            // 如果存在，则删除旧的验证码
            redisTemplate.delete(email);
        }

        // 生成一个唯一的注册码
        String registerCode = UUID.randomUUID().toString().substring(0, 8);

        // 将新的验证码存储到Redis中，设置过期时间为5分钟
        redisTemplate.opsForValue().set(email, registerCode, 5, TimeUnit.MINUTES);

        return registerCode;
    }

    @Override
    public String isEmailExist(String email) {
        return adminMapper.isEmailExist(email);
    }

    @Override
    public boolean validateRegisterCode(String email, String registerCode) {
        String storedRegisterCode = redisTemplate.opsForValue().get(email);
        if (storedRegisterCode == null || !storedRegisterCode.equals(registerCode)) {
            return false;
        }
        return true;
    }
}

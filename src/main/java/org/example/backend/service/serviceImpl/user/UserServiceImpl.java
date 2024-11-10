package org.example.backend.service.serviceImpl.user;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import org.example.backend.entity.user.User;
import org.example.backend.mapper.user.UserMapper;
import org.example.backend.service.user.UserService;
import org.example.backend.util.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserMapper userMapper;

  @Override
  public User selectById(String userId) {
    try {
      return userMapper.selectById(userId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取用户失败，userId: {}", userId, e);
      return null;
    }
  }

  @Override
  public List<User> selectAll() {
    try {
      return userMapper.selectAll();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取所有用户失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public String insert(User user) {
    try {
      String userId = "U-" + UUID.randomUUID();
      user.setUserId(userId);
      //String username = EncryptionUtil.encryptMD5(user.getUsername());
      //user.setUsername(username);
      //String password = EncryptionUtil.encryptMD5(user.getPassword());
      //user.setPassword(password);
      user.setStatus("active");
      user.setRegistrationDate(LocalDateTime.now());
      user.setLastLogin(LocalDateTime.now());
      userMapper.insertUser(user);
      logger.info("User with ID {} inserted successfully", user.getUserId());
      return userId;
    } catch (Exception e) {
      logger.error("Error inserting user with ID {}: {}", user.getUserId(), e.getMessage(), e);
      return null;
    }
  }

  @Override
  public boolean update(User user) {
    try {
      String sessionKey = EncryptionUtil.encryptMD5(user.getSessionKey());
      user.setSessionKey(sessionKey);
      userMapper.updateUser(user);
      logger.info("User with ID {} updated successfully", user.getUserId());
      return true;
    } catch (Exception e) {
      logger.error("Error updating user with ID {}: {}", user.getUserId(), e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean delete(String userId) {

    try {
      userMapper.deleteById(userId);
      logger.info("User with ID {} deleted successfully", userId);
      return true;
    } catch (Exception e) {
      logger.error("Error deleting user with ID {}: {}", userId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public String register(User user){
    try {
      String userId = "U-" + UUID.randomUUID();
      user.setUserId(userId);
      String openid = EncryptionUtil.encryptMD5(user.getOpenid());
      user.setOpenid(openid);
      String sessionKey = EncryptionUtil.encryptMD5(user.getSessionKey());
      user.setSessionKey(sessionKey);
      user.setStatus("active");
      user.setRegistrationDate(LocalDateTime.now());
      user.setLastLogin(LocalDateTime.now());
      userMapper.insertUser(user);
      logger.info("User with ID {} registered successfully", userId);
      return userId;
    } catch (Exception e) {
      logger.error("Error registering user with ID {}: {}", user.getUserId(), e.getMessage(), e);
      return null;
    }
  }

  //根据openid查询用户
  @Override
  public User selectByOpenId(String openid) {
    try {
      return userMapper.selectByOpenId(openid);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取用户失败，openid: {}", openid, e);
      return null;
    }
  }
}

package org.example.backend.service.serviceImpl.user;

import java.time.LocalDateTime;
import java.util.UUID;
import org.example.backend.entity.user.User;
import org.example.backend.mapper.user.UserMapper;
import org.example.backend.service.user.UserService;
import org.example.backend.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public User getById(String userId) {
    return userMapper.selectById(userId);
  }

  @Override
  public List<User> getAll() {
    return userMapper.selectAll();
  }

  @Override
  public String insertUser(User user) {
    try {
      String userId = "U-" + UUID.randomUUID();
      String username = EncryptionUtil.encryptMD5(user.getUsername());
      user.setUsername(username);
      String password = EncryptionUtil.encryptMD5(user.getPassword());
      user.setUserId(userId);
      user.setPassword(password);
      user.setStatus("active");
      user.setRegistrationDate(LocalDateTime.now());
      userMapper.insertUser(user);
      return userId;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean updateUser(User user) {
    try {
      userMapper.updateUser(user);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean deleteUser(String userId) {

    try {
      userMapper.deleteById(userId);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public String registerUser(User user){
    try {
      String userId = "U-" + UUID.randomUUID();
      String username = EncryptionUtil.encryptMD5(user.getUsername());
      user.setUsername(username);
      String password = EncryptionUtil.encryptMD5(user.getPassword());
      user.setUserId(userId);
      user.setPassword(password);
      user.setStatus("active");
      user.setRegistrationDate(LocalDateTime.now());
      userMapper.insertUser(user);
      return userId;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

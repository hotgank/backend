package org.example.backend.service.serviceImpl.user;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import org.example.backend.dto.UserGetDoctorDTO;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.user.User;
import org.example.backend.mapper.doctor.DoctorMapper;
import org.example.backend.mapper.doctor.DoctorUserRelationMapper;
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

  @Autowired private UserMapper userMapper;

  @Autowired private EncryptionUtil encryptionUtil;

  @Autowired private DoctorMapper doctorMapper;

  @Autowired private DoctorUserRelationMapper doctorUserRelationMapper;

  @Override
  public void updateUsername(String userId, String username) {
    try {
      userMapper.updateUsername(userId, username);
      logger.info("更新用户昵称成功");
    } catch (Exception e) {
      logger.error("更新用户昵称失败", e);
    }
  }

  @Override
  public void updateAvatarUrl(String userId, String avatarUrl) {
    try {
      userMapper.updateAvatarUrl(userId, avatarUrl);
      logger.info("更新用户头像成功");
    } catch (Exception e) {
      logger.error("更新用户头像失败", e);
    }
  }

  @Override
  public int selectUserCount() {
    try {
      return userMapper.selectUserCount();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取用户数量失败", e);
      return 0;
    }
  }

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
      // String username = EncryptionUtil.encryptMD5(user.getUsername());
      // user.setUsername(username);
      // String password = EncryptionUtil.encryptMD5(user.getPassword());
      // user.setPassword(password);
      user.setStatus("active");
      user.setRegistrationDate(LocalDateTime.now());
      userMapper.insertUser(user);
      logger.info("User with ID {} inserted successfully", user.getUserId());
      return userId;
    } catch (Exception e) {
      logger.error("Error inserting user with ID {}: {}", user.getUserId(), e.getMessage(), e);
      return null;
    }
  }

  @Override
  public boolean insertAllUser(List<User> users) {
    try {
      for (User user : users) {
        insert(user);
      }
      return true;
    } catch (Exception e) {
      logger.error("Error inserting users: {}", e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean update(User user) {
    try {
      String sessionKey = encryptionUtil.encryptMD5(user.getSessionKey());
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
  public boolean banAccount(String userId) {
    try {
      User user = selectById(userId);
      user.setStatus("disabled");
      userMapper.updateUser(user);
      logger.info("User with ID {} banned successfully", userId);
      return true;
    } catch (Exception e) {
      logger.error("Error banning user with ID {}: {}", userId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean activeAccount(String userId) {
    try {
      User user = selectById(userId);
      user.setStatus("active");
      userMapper.updateUser(user);
      logger.info("User with ID {} activated successfully", userId);
      return true;
    } catch (Exception e) {
      logger.error("Error active user with ID {}: {}", userId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean editUsername(String userId, String username) {
    try {
      User user = selectById(userId);
      user.setUsername(username);
      user.setStatus("active");
      userMapper.updateUser(user);
      logger.info("User with ID {} edited successfully", userId);
      return true;
    } catch (Exception e) {
      logger.error("Error edit user with ID {}: {}", userId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public String register(User user) {
    try {
      String userId = "U-" + UUID.randomUUID();
      user.setUserId(userId);
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

  // 根据openid查询用户
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

  // 用户获取所有已认证医生
  @Override
  public List<UserGetDoctorDTO> selectAllQualifiedDoctors(String userId) {
    try {
      // 根据userId获取所有关系表数据
      List<DoctorUserRelation> relations =
          doctorUserRelationMapper.selectDoctorUserRelationsByUserId(userId);
      List<UserGetDoctorDTO> userGetDoctorDTO = doctorMapper.selectAllQualifiedDoctors();
      // 遍历userGetDoctorDTO,和relations进行匹配，如果匹配上，则设置isMyDoctor为1，否则为0
      for (UserGetDoctorDTO userGetDoctorDTO1 : userGetDoctorDTO) {
        for (DoctorUserRelation relation : relations) {
          if (userGetDoctorDTO1.getDoctorId().equals(relation.getDoctorId())) {
            userGetDoctorDTO1.setSituation(relation.getRelationStatus());
          }
        }
      }

      return userGetDoctorDTO;
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取医生列表失败", e);
      return Collections.emptyList();
    }
  }
}

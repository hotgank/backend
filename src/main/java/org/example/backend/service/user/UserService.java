package org.example.backend.service.user;

import java.util.List;
import org.example.backend.dto.UserGetDoctorDTO;
import org.example.backend.entity.user.User;

public interface UserService {

  int selectUserCount();
  void updateAvatarUrl(String userId, String avatarUrl);
  void updateUsername(String userId, String username);
  User selectById(String userId);
  List<User> selectAll();
  String insert(User user);

  boolean insertAllUser(List<User> users);

  boolean update(User user);
  boolean delete(String userId);

  boolean banAccount(String userId); boolean activeAccount(String userId); boolean editUsername(String userId, String username);String register(User user);

  //根据openid查询用户
  User selectByOpenId(String openid);

  //用户获取已认证的医生列表
  List<UserGetDoctorDTO> selectAllQualifiedDoctors(String userId);
}

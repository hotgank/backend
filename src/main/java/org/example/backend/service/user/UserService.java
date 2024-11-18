package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.User;

public interface UserService {
  User selectById(String userId);
  List<User> selectAll();
  String insert(User user);

  boolean insertAllUser(List<User> users);

  boolean update(User user);
  boolean delete(String userId);

  boolean banAccount(String userId); boolean activeAccount(String userId); boolean editUsername(String userId, String username);String register(User user);

  //根据openid查询用户
  User selectByOpenId(String openid);
}

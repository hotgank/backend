package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.User;

public interface UserService {
  User selectById(String userId);
  List<User> selectAll();
  String insert(User user);
  boolean update(User user);
  boolean delete(String userId);

  String register(User user);
}

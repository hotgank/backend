package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.User;

public interface UserService {
  User getById(String userId);
  List<User> getAll();
  boolean createUser(User user);
  boolean updateUser(User user);
  boolean deleteUser(String userId);
}

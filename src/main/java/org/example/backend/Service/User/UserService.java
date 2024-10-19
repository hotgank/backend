package org.example.backend.Service.User;

import java.util.List;
import org.example.backend.Entity.User.User;

public interface UserService {
  User getById(String userId);
  List<User> getAll();
  boolean createUser(User user);
  boolean updateUser(User user);
  boolean deleteUser(String userId);
}

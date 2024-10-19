package org.example.backend.Service.ServiceImpl.User;

import org.example.backend.Entity.User.User;
import org.example.backend.Mapper.User.UserMapper;
import org.example.backend.Service.User.UserService;
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
    return userMapper.selectList(null);
  }

  @Override
  public boolean createUser(User user) {
    return userMapper.insert(user) > 0;
  }

  @Override
  public boolean updateUser(User user) {
    return userMapper.updateById(user) > 0;
  }

  @Override
  public boolean deleteUser(String userId) {
    return userMapper.deleteById(userId) > 0;
  }
}

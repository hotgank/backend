package org.example.backend.mapper.user;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.user.User;

@Mapper
public interface UserMapper {
  // 查询所有用户信息
  @Select("SELECT * FROM u_users")
  List<User> selectAll();

  // 根据ID查询
  @Select("SELECT * FROM u_users WHERE user_id = #{childId}")
  User selectById(String childId);

  // 插入用户信息
  @Insert("INSERT INTO u_users(user_id, username, password, email, phone, registration_date,"
      + " last_login, status, avatar_url) "
      + "VALUES(#{userId}, #{username}, #{password}, #{email}, #{phone}, #{registrationDate},"
      + " #{lastLogin}, #{status}, #{avatarUrl})")
  void insertUser(User user);

  // 更新用户信息
  @Update("UPDATE u_users SET "
      + "username = #{username}, "
      + "password = #{password}, "
      + "email = #{email}, "
      + "phone = #{phone}, "
      + "registration_date = #{registrationDate}, "
      + "last_login = #{lastLogin}, "
      + "status = #{status}, "
      + "avatar_url = #{avatarUrl} "
      + "WHERE user_id = #{userId}")
  void updateUser(User user);

  // 删除用户
  @Insert("DELETE FROM u_users WHERE user_id = #{userId}")
  void deleteById(String userId);
}
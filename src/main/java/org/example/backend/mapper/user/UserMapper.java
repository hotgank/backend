package org.example.backend.mapper.user;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.user.User;

@Mapper
public interface UserMapper {

  // 查询用户总数
  @Select("SELECT COUNT(*) FROM u_users")
  int selectUserCount();

  // 查询所有用户信息
  @Select("SELECT * FROM u_users")
  @Results({
    @Result(column = "user_id", property = "userId"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "email", property = "email"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status"),
    @Result(column = "avatar_url", property = "avatarUrl")
  })
  List<User> selectAll();

  // 根据ID查询
  @Select("SELECT * FROM u_users WHERE user_id = #{userId}")
  @Results({
    @Result(column = "user_id", property = "userId"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "email", property = "email"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "openid", property = "openid"),
    @Result(column = "session_key", property = "sessionKey")
  })
  User selectById(@Param("userId") String userId);

  // 插入用户信息
  @Insert(
      "INSERT INTO u_users (user_id, username, password, email, phone, registration_date, last_login, status, avatar_url, openid, session_key) "
          + "VALUES (#{userId}, #{username}, #{password}, #{email}, #{phone}, #{registrationDate}, #{lastLogin}, #{status}, #{avatarUrl}, #{openid}, #{sessionKey})")
  void insertUser(User user);

  // 更新用户信息
  @Update(
      "UPDATE u_users SET "
          + "username = #{username}, "
          + "password = #{password}, "
          + "email = #{email}, "
          + "phone = #{phone}, "
          + "registration_date = #{registrationDate}, "
          + "last_login = #{lastLogin}, "
          + "status = #{status} "
          + "WHERE user_id = #{userId}")
  void updateUser(User user);

  @Update("UPDATE u_users SET avatar_url = #{avatarUrl} WHERE user_id = #{userId}")
  void updateAvatarUrl(@Param("userId") String userId, @Param("avatarUrl") String avatarUrl);

  @Update("UPDATE u_users SET username = #{username} WHERE user_id = #{userId}")
  void updateUsername(@Param("userId") String userId, @Param("username") String username);

  // 删除用户
  @Insert("DELETE FROM u_users WHERE user_id = #{userId}")
  void deleteById(@Param("userId") String userId);

  // 根据openid查询用户
  @Select("SELECT * FROM u_users WHERE openid = #{openid}")
  @Results({
    @Result(column = "user_id", property = "userId"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "email", property = "email"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "registration_date", property = "registrationDate"),
  })
  User selectByOpenId(@Param("openid") String openid);
}

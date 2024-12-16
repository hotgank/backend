package org.example.backend.mapper.admin;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.admin.Admin;

@Mapper
public interface AdminMapper {

  @Select("SELECT * FROM a_admins")
  @Results({
    @Result(column = "admin_id", property = "adminId"),
    @Result(column = "admin_type", property = "adminType"),
    @Result(column = "supervisor_id", property = "supervisorId"),
    @Result(column = "unit_name", property = "unitName"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "email", property = "email"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status")
  })
  List<Admin> selectAll();

  @Select("SELECT * FROM a_admins WHERE admin_type = 'second'")
  @Results({
          @Result(column = "admin_id", property = "adminId"),
          @Result(column = "admin_type", property = "adminType"),
          @Result(column = "supervisor_id", property = "supervisorId"),
          @Result(column = "unit_name", property = "unitName"),
          @Result(column = "username", property = "username"),
          @Result(column = "password", property = "password"),
          @Result(column = "email", property = "email"),
          @Result(column = "phone", property = "phone"),
          @Result(column = "avatar_url", property = "avatarUrl"),
          @Result(column = "registration_date", property = "registrationDate"),
          @Result(column = "last_login", property = "lastLogin"),
          @Result(column = "status", property = "status")
  })
  List<Admin> selectSecondAdmins();

  @Select("SELECT * FROM a_admins WHERE admin_id = #{adminId}")
  @Results({
    @Result(column = "admin_id", property = "adminId"),
    @Result(column = "admin_type", property = "adminType"),
    @Result(column = "supervisor_id", property = "supervisorId"),
    @Result(column = "unit_name", property = "unitName"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "email", property = "email"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status")
  })
  Admin selectById(@Param("adminId") String adminId);

  @Select("SELECT * FROM a_admins WHERE email = #{email}")
  @Results({
    @Result(column = "admin_id", property = "adminId"),
    @Result(column = "admin_type", property = "adminType"),
    @Result(column = "supervisor_id", property = "supervisorId"),
    @Result(column = "unit_name", property = "unitName"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "email", property = "email"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status")
  })
  Admin selectAdminByEmail(@Param("email") String email);

  @Select("SELECT * FROM a_admins WHERE username = #{username}")
  @Results({
    @Result(column = "admin_id", property = "adminId"),
    @Result(column = "admin_type", property = "adminType"),
    @Result(column = "supervisor_id", property = "supervisorId"),
    @Result(column = "unit_name", property = "unitName"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "email", property = "email"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status")
  })
  Admin selectAdminByUsername(@Param("username") String username);

  @Insert(
      "INSERT INTO a_admins(admin_id, admin_type, supervisor_id, unit_name, username, "
          + "password, email, phone, avatar_url, registration_date, last_login, status)"
          + "VALUES(#{adminId}, #{adminType}, #{supervisorId}, #{unitName}, #{username}, "
          + "#{password}, #{email}, #{phone}, #{avatarUrl}, #{registrationDate}, "
          + "#{lastLogin}, #{status})")
  void insertAdmin(Admin admin);

  @Update(
      "UPDATE a_admins SET "
          + "admin_type = #{adminType}, "
          + "supervisor_id = #{supervisorId}, "
          + "unit_name = #{unitName}, "
          + "username = #{username}, "
          + "password = #{password}, "
          + "email = #{email}, "
          + "phone = #{phone}, "
          + "avatar_url = #{avatarUrl}, "
          + "registration_date = #{registrationDate}, "
          + "last_login = #{lastLogin}, "
          + "status = #{status} "
          + "WHERE admin_id = #{adminId}")
  void updateAdmin(Admin admin);

  @Delete("DELETE FROM a_admins WHERE admin_id = #{adminId}")
  void deleteAdmin(@Param("adminId") String adminId);
}

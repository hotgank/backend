package org.example.backend.mapper.admin;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.admin.Admin;

@Mapper
public interface AdminMapper{

  @Select("SELECT * FROM a_admins")
  List<Admin> selectAll();

  @Select("SELECT * FROM a_admins WHERE admin_id = #{adminId}")
  Admin selectById(String adminId);

  @Insert("INSERT INTO a_admins(admin_id, admin_type, supervisor_id, unit_name, username, "
      + "password, email, phone, avatar_url, registration_date, last_login, status)"
      + "VALUES(#{adminId}, #{adminType}, #{supervisorId}, #{unitName}, #{username}, "
      + "#{password}, #{email}, #{phone}, #{avatarUrl}, #{registrationDate}, "
      + "#{lastLogin}, #{status})")
  void insertAdmin(Admin admin);

  @Update("UPDATE a_admins SET "
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
  void deleteAdmin(String adminId);
}

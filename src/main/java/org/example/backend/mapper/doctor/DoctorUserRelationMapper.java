package org.example.backend.mapper.doctor;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.user.Child;
import org.example.backend.entity.user.User;

@Mapper
public interface DoctorUserRelationMapper {
  @Select("SELECT * FROM u_users WHERE user_id IN "
      + "(SELECT user_id FROM d_doctors_users WHERE doctor_id = #{doctorId} AND relation_status = #{relationStatus} ORDER BY created_at)")
  @Results({
      @Result(column = "user_id", property = "userId"),
      @Result(column = "username", property = "username"),
      @Result(column = "email", property = "email"),
      @Result(column = "phone", property = "phone"),
      @Result(column = "avatar_url", property = "avatarUrl"),
      @Result(column = "registration_date", property = "registrationDate"),
      @Result(column = "last_login", property = "lastLogin"),
      @Result(column = "status", property = "status")
  })
  List<User> selectMyPatients(@Param("doctorId") String doctorId, @Param("relationStatus") String relationStatus);

  @Select("SELECT * FROM u_users WHERE user_id IN "
          + "(SELECT user_id FROM d_doctors_users "
          + "WHERE doctor_id = #{doctorId} AND relation_status = #{relationStatus} "
          + "ORDER BY created_at DESC LIMIT 5)")
  @Results({
      @Result(column = "user_id", property = "userId"),
      @Result(column = "username", property = "username"),
      @Result(column = "email", property = "email"),
      @Result(column = "phone", property = "phone"),
      @Result(column = "avatar_url", property = "avatarUrl"),
      @Result(column = "registration_date", property = "registrationDate"),
      @Result(column = "last_login", property = "lastLogin"),
      @Result(column = "status", property = "status")
  })
  List<User> selectRecentPatients(@Param("doctorId") String doctorId, @Param("relationStatus") String relationStatus);

  @Insert("INSERT INTO d_doctors_users(doctor_id, user_id, relation_status, created_at) "
      + "VALUES(#{doctorId}, #{userId}, #{relationStatus}, #{createdAt})")
  @Options(useGeneratedKeys = true, keyProperty = "relationId")
  int createDoctorUserRelation(DoctorUserRelation relation);

  @Update("UPDATE d_doctors_users SET relation_status = #{relationStatus} "
      + "WHERE user_id = #{userId} AND doctor_id = #{doctorId}")
  boolean updateDoctorUserRelation(DoctorUserRelation relation);

  @Delete("DELETE FROM d_doctors_users WHERE doctor_id = #{doctorId} AND user_id = #{userId}")
  int deleteDoctorUserRelation(DoctorUserRelation relation);
}
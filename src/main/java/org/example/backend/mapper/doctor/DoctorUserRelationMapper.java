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
import org.example.backend.dto.DoctorGetUserBindingDTO;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.others.DoctorWithStatus;
import org.example.backend.entity.user.User;

@Mapper
public interface DoctorUserRelationMapper {
  @Select(
      "SELECT d.*, dur.relation_status FROM d_doctors d "
          + "JOIN d_doctors_users dur ON d.doctor_id = dur.doctor_id "
          + "WHERE dur.user_id = #{userId} ")
  @Results({
    @Result(column = "name", property = "doctor.name"),
    @Result(column = "workplace", property = "doctor.workplace"),
    @Result(column = "doctor_id", property = "doctor.doctorId"),
    @Result(column = "username", property = "doctor.username"),
    @Result(column = "email", property = "doctor.email"),
    @Result(column = "phone", property = "doctor.phone"),
    @Result(column = "avatar_url", property = "doctor.avatarUrl"),
    @Result(column = "rating", property = "doctor.rating"),
    @Result(column = "experience", property = "doctor.experience"),
    @Result(column = "position", property = "doctor.position"),
    @Result(column = "registration_date", property = "doctor.registrationDate"),
    @Result(column = "relation_status", property = "relationStatus")
  })
  List<DoctorWithStatus> selectApplications(@Param("userId") String userId);

  @Select(
      "SELECT * FROM d_doctors "
          + "WHERE doctor_id IN (SELECT doctor_id FROM d_doctors_users WHERE user_id = #{userId} AND relation_status = 'approved')")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "username", property = "username"),
    @Result(column = "email", property = "email"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "registration_date", property = "registrationDate"),
  })
  List<Doctor> selectDoctorsByUserId(@Param("userId") String userId);

  @Select(
      "SELECT * FROM u_users WHERE user_id IN "
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
  List<User> selectMyPatients(
      @Param("doctorId") String doctorId, @Param("relationStatus") String relationStatus);

  @Select(
      "SELECT * FROM u_users WHERE user_id IN "
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
  List<User> selectRecentPatients(
      @Param("doctorId") String doctorId, @Param("relationStatus") String relationStatus);

  // 查询d_doctors_users表中relationStatus为pending的关系，再查询u_users表的username列，并按时间顺序从近到远返回关系，
  @Select(
      "SELECT d.doctor_id, d.user_id, d.relation_id, d.relation_status, u.username "
          + "FROM d_doctors_users d "
          + "JOIN u_users u ON d.user_id = u.user_id "
          + "WHERE d.doctor_id = #{doctorId} AND d.relation_status = #{relationStatus} "
          + "ORDER BY d.created_at DESC")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "user_id", property = "userId"),
    @Result(column = "relation_id", property = "relationId"),
    @Result(column = "relation_status", property = "relationStatus"),
    @Result(column = "username", property = "username"),
    @Result(column = "created_at", property = "createdAt")
  })
  List<DoctorGetUserBindingDTO> selectPendingPatients(
      @Param("doctorId") String doctorId, @Param("relationStatus") String relationStatus);

  @Insert(
      "INSERT INTO d_doctors_users(doctor_id, user_id, relation_status, created_at) "
          + "VALUES(#{doctorId}, #{userId}, #{relationStatus}, #{createdAt})")
  @Options(useGeneratedKeys = true, keyProperty = "relationId")
  int createDoctorUserRelation(DoctorUserRelation relation);

  @Update(
      "UPDATE d_doctors_users SET relation_status = #{relationStatus} "
          + "WHERE user_id = #{userId} AND doctor_id = #{doctorId}")
  boolean updateDoctorUserRelation(DoctorUserRelation relation);

  @Delete("DELETE FROM d_doctors_users WHERE doctor_id = #{doctorId} AND user_id = #{userId}")
  int deleteDoctorUserRelation(DoctorUserRelation relation);

  @Select("SELECT * FROM d_doctors_users WHERE doctor_id = #{doctorId} AND user_id = #{userId}")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "user_id", property = "userId"),
    @Result(column = "relation_id", property = "relationId"),
    @Result(column = "relation_status", property = "relationStatus"),
      @Result(column = "created_at", property = "createdAt")
  })
  DoctorUserRelation selectDoctorUserRelation(
      @Param("doctorId") String doctorId, @Param("userId") String userId);

  // 根据医生ID查询医生用户关系
  @Select("SELECT * FROM d_doctors_users WHERE doctor_id = #{doctorId}")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "user_id", property = "userId"),
    @Result(column = "relation_id", property = "relationId"),
    @Result(column = "relation_status", property = "relationStatus"),
  })
  List<DoctorUserRelation> findRelationsByDoctorId(@Param("doctorId") String doctorId);

  // 根据用户ID查询医生用户关系
  @Select("SELECT * FROM d_doctors_users WHERE user_id = #{userId}")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "user_id", property = "userId"),
    @Result(column = "relation_id", property = "relationId"),
    @Result(column = "relation_status", property = "relationStatus"),
  })
  List<DoctorUserRelation> findRelationsByUserId(@Param("userId") String userId);

  // 根据关系ID查询关系
  @Select("SELECT * FROM d_doctors_users WHERE relation_id = #{relationId}")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "user_id", property = "userId"),
    @Result(column = "relation_id", property = "relationId"),
    @Result(column = "relation_status", property = "relationStatus"),
      @Result(column = "created_at", property = "createdAt")
  })
  DoctorUserRelation findRelationById(@Param("relationId") Integer relationId);

  // 根据userId查询d_doctors_users表中的记录
  @Select("SELECT * FROM d_doctors_users WHERE user_id = #{userId}")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "user_id", property = "userId"),
    @Result(column = "relation_id", property = "relationId"),
    @Result(column = "relation_status", property = "relationStatus"),
  })
  List<DoctorUserRelation> selectDoctorUserRelationsByUserId(@Param("userId") String userId);

  // 多表查询，根据医生ID查询状态为removeBinding的关联关系，然后再查询u_users表的username列
  @Select(
      "SELECT d_doctors_users.*, u_users.username "
          + "FROM d_doctors_users "
          + "JOIN u_users ON d_doctors_users.user_id = u_users.user_id "
          + "WHERE d_doctors_users.doctor_id = #{doctorId} AND d_doctors_users.relation_status = 'removeBinding'")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "user_id", property = "userId"),
    @Result(column = "relation_id", property = "relationId"),
    @Result(column = "relation_status", property = "relationStatus"),
    @Result(column = "created_at", property = "createdAt"),
    @Result(column = "username", property = "username")
  })
  List<DoctorGetUserBindingDTO> selectRemoveBindingRelations(@Param("doctorId") String doctorId);
}

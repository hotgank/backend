package org.example.backend.mapper.doctor;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.example.backend.dto.UserGetDoctorDTO;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.util.SqlProvider.DoctorSqlProvider;

@Mapper
public interface DoctorMapper {

  @Select("SELECT COUNT(*) FROM d_doctors")
  int selectDoctorCount();

  @Select("SELECT COUNT(*) FROM d_doctors " +
          "WHERE workplace IN (SELECT hospital_name FROM o_hospitals WHERE admin_id = #{adminId})" +
          " OR " +
          "((SELECT admin_type FROM a_admins WHERE admin_id = #{adminId}) != 'second' AND workplace IS NULL)" +
          " OR " +
          "((SELECT admin_type FROM a_admins WHERE admin_id = #{adminId}) = 'super')")
  int selectMyDoctorCount(@Param("adminId") String adminId);

  @Select("SELECT COUNT(*) FROM d_doctors WHERE qualification IS NULL")
  int selectUnqualifiedDoctorCount();

  @Select("SELECT * FROM d_doctors WHERE workplace In " +
          "(SELECT hospital_name FROM o_hospitals WHERE admin_id IS NULL)")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "name", property = "name"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "email", property = "email"),
    @Result(column = "age", property = "age"),
    @Result(column = "gender", property = "gender"),
    @Result(column = "position", property = "position"),
    @Result(column = "workplace", property = "workplace"),
    @Result(column = "qualification", property = "qualification"),
    @Result(column = "experience", property = "experience"),
    @Result(column = "rating", property = "rating"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status")
  })
  List<Doctor> selectAll();

  @Select("SELECT * FROM d_doctors WHERE doctor_id = #{doctorId}")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "name", property = "name"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "email", property = "email"),
    @Result(column = "age", property = "age"),
    @Result(column = "gender", property = "gender"),
    @Result(column = "position", property = "position"),
    @Result(column = "workplace", property = "workplace"),
    @Result(column = "qualification", property = "qualification"),
    @Result(column = "experience", property = "experience"),
    @Result(column = "rating", property = "rating"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status")
  })
  Doctor selectById(String doctorId);

  @Insert(
      "INSERT INTO d_doctors(doctor_id, name, username,password, phone, email, "
          + "birthdate, gender, position, workplace, qualification, experience, rating, "
          + "avatar_url, registration_date, last_login, status) "
          + "VALUES (#{doctorId}, #{name}, #{username} ,#{password}, #{phone}, #{email}, "
          + "#{birthdate}, #{gender}, #{position}, #{workplace}, #{qualification}, #{experience}, "
          + "#{rating}, #{avatarUrl}, #{registrationDate}, #{lastLogin}, #{status})")
  void insertDoctor(Doctor doctor);

  @Update(
      "UPDATE d_doctors "
          + "SET name = #{name}, "
          + "username = #{username}, "
          + "password = #{password}, "
          + "phone = #{phone}, "
          + "email = #{email}, "
          + "birthdate = #{birthdate}, "
          + "gender = #{gender}, "
          + "position = #{position}, "
          + "workplace = #{workplace}, "
          + "qualification = #{qualification}, "
          + "experience = #{experience}, "
          + "rating = #{rating}, "
          + "avatar_url = #{avatarUrl}, "
          + "last_login = #{lastLogin}, "
          + "status = #{status} "
          + "WHERE doctor_id = #{doctorId}")
  void updateDoctor(Doctor doctor);

  @Delete("DELETE FROM d_doctors WHERE doctor_id = #{doctorId}")
  void deleteDoctor(@Param("doctorId") String doctorId);

  @Select("SELECT * FROM d_doctors WHERE email = #{email}")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "name", property = "name"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "email", property = "email"),
    @Result(column = "age", property = "age"),
    @Result(column = "gender", property = "gender"),
    @Result(column = "position", property = "position"),
    @Result(column = "workplace", property = "workplace"),
    @Result(column = "qualification", property = "qualification"),
    @Result(column = "experience", property = "experience"),
    @Result(column = "rating", property = "rating"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status")
  })
  Doctor selectDoctorByEmail(@Param("email") String email);

  @Select("SELECT * FROM d_doctors WHERE username = #{username}")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "name", property = "name"),
    @Result(column = "username", property = "username"),
    @Result(column = "password", property = "password"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "email", property = "email"),
    @Result(column = "age", property = "age"),
    @Result(column = "gender", property = "gender"),
    @Result(column = "position", property = "position"),
    @Result(column = "workplace", property = "workplace"),
    @Result(column = "qualification", property = "qualification"),
    @Result(column = "experience", property = "experience"),
    @Result(column = "rating", property = "rating"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "registration_date", property = "registrationDate"),
    @Result(column = "last_login", property = "lastLogin"),
    @Result(column = "status", property = "status")
  })
  Doctor selectDoctorByUsername(@Param("username") String username);

  @Select("SELECT doctor_id FROM d_doctors WHERE username = #{username}")
  String isUsernameExist(@Param("username") String username);

  @Select("SELECT doctor_id FROM d_doctors WHERE email = #{email}")
  String isEmailExist(@Param("email") String email);

  // 获取qualification为"已认证"的所有医生,并且status为"active"
  @Select(
      "SELECT doctor_id, name, username, phone, email, gender, position, workplace, qualification, experience, rating, avatar_url, status, birthdate "
          + "FROM d_doctors "
          + "WHERE qualification = '已认证' AND status = 'active'")
  @Results({
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "name", property = "name"),
    @Result(column = "username", property = "username"),
    @Result(column = "phone", property = "phone"),
    @Result(column = "email", property = "email"),
    @Result(column = "gender", property = "gender"),
    @Result(column = "position", property = "position"),
    @Result(column = "workplace", property = "workplace"),
    @Result(column = "qualification", property = "qualification"),
    @Result(column = "experience", property = "experience"),
    @Result(column = "rating", property = "rating"),
    @Result(column = "avatar_url", property = "avatarUrl"),
    @Result(column = "status", property = "status"),
    @Result(column = "birthdate", property = "birthdate")
  })
  List<UserGetDoctorDTO> selectAllQualifiedDoctors();

  /**
   * 根据查询条件分页查询所有医生
   *
   * @param queryString 查询条件
   * @param PageBegin 开始页
   * @param PageSize 每页数量
   * @return 分页查询结果
   */
  @SelectProvider(type = DoctorSqlProvider.class, method = "selectDoctorByCondition")
  @Results({
      @Result(column = "doctor_id", property = "doctorId"),
      @Result(column = "name", property = "name"),
      @Result(column = "username", property = "username"),
      @Result(column = "password", property = "password"),
      @Result(column = "phone", property = "phone"),
      @Result(column = "email", property = "email"),
      @Result(column = "age", property = "age"),
      @Result(column = "gender", property = "gender"),
      @Result(column = "position", property = "position"),
      @Result(column = "workplace", property = "workplace"),
      @Result(column = "qualification", property = "qualification"),
      @Result(column = "experience", property = "experience"),
      @Result(column = "rating", property = "rating"),
      @Result(column = "avatar_url", property = "avatarUrl"),
      @Result(column = "registration_date", property = "registrationDate"),
      @Result(column = "last_login", property = "lastLogin"),
      @Result(column = "status", property = "status")
  })
  List<Doctor> selectDoctorByCondition(@Param("queryString") String queryString,
                                       @Param("adminId") String adminId,
                                       @Param("pageBegin") int PageBegin,
                                       @Param("pageSize") int PageSize);
}

package org.example.backend.mapper.doctor;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.doctor.Doctor;

@Mapper
public interface DoctorMapper{
  @Select("SELECT * FROM d_doctors")
  List<Doctor> selectAll();

  @Select("SELECT * FROM d_doctors WHERE doctor_id = #{doctorId}")
  Doctor selectById(String doctorId);

  @Insert("INSERT INTO d_doctors(doctor_id, name, password, phone, email, "
      + "age, gender, position, workplace, qualification, experience, rating, "
      + "avatar_url, registration_date, last_login, status) "
      + "VALUES (#{doctorId}, #{name}, #{password}, #{phone}, #{email}, "
      + "#{age}, #{gender}, #{position}, #{workplace}, #{qualification}, #{experience}, "
      + "#{rating}, #{avatarUrl}, #{registrationDate}, #{lastLogin}, #{status})")
  void insertDoctor(Doctor doctor);

  @Update("UPDATE d_doctors "
      + "SET name = #{name}, "
      + "password = #{password}, "
      + "phone = #{phone}, "
      + "email = #{email}, "
      + "age = #{age}, "
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
  void deleteDoctor(String doctorId);
}
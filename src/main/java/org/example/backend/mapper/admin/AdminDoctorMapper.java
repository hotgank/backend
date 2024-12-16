package org.example.backend.mapper.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.doctor.Doctor;

import java.util.List;

@Mapper
public interface AdminDoctorMapper {
    @Select("SELECT * FROM d_doctors WHERE workplace In " +
            "(SELECT hospital_name FROM o_hospitals where admin_id is null)")
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
    List<Doctor> selectMyDoctors();
}

package org.example.backend.mapper.others;

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
import org.example.backend.entity.user.Consultation;

@Mapper
public interface ConsultationMapper {
  @Select("SELECT * FROM c_consultations WHERE consultation_id = #{consultationId}")
  @Results({
      @Result(column = "consultation_id", property = "consultationId"),
      @Result(column = "doctor_id", property = "doctorId"),
      @Result(column = "user_id", property = "userId"),
      @Result(column = "consultation_start", property = "consultationStart"),
      @Result(column = "consultation_end", property = "consultationEnd"),
      @Result(column = "rating", property = "rating"),
  })
  Consultation selectById(@Param("consultationId") int consultationId);

  @Select("SELECT * FROM c_consultations WHERE doctor_id = #{doctorId}")
  @Results({
      @Result(column = "consultation_id", property = "consultationId"),
      @Result(column = "doctor_id", property = "doctorId"),
      @Result(column = "user_id", property = "userId"),
      @Result(column = "consultation_start", property = "consultationStart"),
      @Result(column = "consultation_end", property = "consultationEnd"),
      @Result(column = "rating", property = "rating"),
  })
  List<Consultation> selectByDoctorId(@Param("doctorId") String doctorId);

  @Select("SELECT * FROM c_consultations WHERE user_id = #{userId}")
  @Results({
      @Result(column = "consultation_id", property = "consultationId"),
      @Result(column = "doctor_id", property = "doctorId"),
      @Result(column = "user_id", property = "userId"),
      @Result(column = "consultation_start", property = "consultationStart"),
      @Result(column = "consultation_end", property = "consultationEnd"),
      @Result(column = "rating", property = "rating"),
  })
  List<Consultation> selectByUserId(@Param("userId") String userId);

  @Select("SELECT * FROM c_consultations WHERE doctor_id = #{doctorId} AND user_id = #{userId}")
  @Results({
      @Result(column = "consultation_id", property = "consultationId"),
      @Result(column = "doctor_id", property = "doctorId"),
      @Result(column = "user_id", property = "userId"),
      @Result(column = "consultation_start", property = "consultationStart"),
      @Result(column = "consultation_end", property = "consultationEnd"),
      @Result(column = "rating", property = "rating")
  })
  Consultation selectConsultationByDoctorIdAndUserId(@Param("doctorId") String doctorId, @Param("userId") String userId);

  @Select("SELECT * FROM c_consultations")
  @Results({
      @Result(column = "consultation_id", property = "consultationId"),
      @Result(column = "doctor_id", property = "doctorId"),
      @Result(column = "user_id", property = "userId"),
      @Result(column = "consultation_start", property = "consultationStart"),
      @Result(column = "consultation_end", property = "consultationEnd"),
      @Result(column = "rating", property = "rating"),
  })
  List<Consultation> selectAll();

  @Insert("INSERT INTO c_consultations(user_id, doctor_id, consultation_start, consultation_end, rating)" +
      "VALUES(#{userId}, #{doctorId}, #{consultationStart}, #{consultationEnd}, #{rating})")
  @Options(useGeneratedKeys = true, keyProperty = "consultationId")
  int insertConsultation(Consultation consultation);

  // 更新咨询记录
  @Update("UPDATE c_consultations SET doctor_id = #{doctorId}, user_id = #{userId}, consultation_start = #{consultationStart}, " +
      "consultation_end = #{consultationEnd}, rating = #{rating} WHERE consultation_id = #{consultationId}")
  int updateConsultation(Consultation consultation);

  // 根据ID删除咨询记录
  @Delete("DELETE FROM c_consultations WHERE consultation_id = #{id}")
  int deleteConsultationById(int id);
}
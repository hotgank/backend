package org.example.backend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.user.Consultation;

@Mapper
public interface ConsultationMapper extends BaseMapper<Consultation> {
  @Select("SELECT * FROM c_consultations WHERE consultation_id = #{consultationId}")
  Consultation selectById(int consultationId);

  @Select("SELECT * FROM c_consultations")
  List<Consultation> selectAll();

  @Insert("INSERT INTO c_consultations(consultation_id, user_id, doctor_id, consultation_start, consultation_end, rating)" +
      "VALUES(#{consultationId}, #{userId}, #{doctorId}, #{consultationStart}, #{consultationEnd}, #{rating})")
  void insertConsultation(Consultation consultation);

  // 更新咨询记录
  @Update("UPDATE c_consultations SET doctor_id = #{doctorId}, user_id = #{userId}, consultation_start = #{consultationStart}, " +
      "consultation_end = #{consultationEnd}, rating = #{rating} WHERE consultation_id = #{consultationId}")
  int updateConsultation(Consultation consultation);

  // 根据ID删除咨询记录
  @Delete("DELETE FROM c_consultations WHERE consultation_id = #{id}")
  int deleteConsultationById(int id);
}
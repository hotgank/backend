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
import org.example.backend.entity.doctor.DoctorChildRelation;
import org.example.backend.entity.user.Child;

@Mapper
public interface DoctorChildRelationMapper {
  @Select("SELECT * FROM u_children WHERE child_id IN "
      + "(SELECT child_id FROM d_doctors_children WHERE doctor_id = #{doctorId} AND relation_status = #{relationStatus} ORDER BY created_at)")
  @Results({
      @Result(column = "child_id", property = "childId"),
      @Result(column = "name", property = "name"),
      @Result(column = "school", property = "school"),
      @Result(column = "gender", property = "gender"),
      @Result(column = "birthdate", property = "birthdate"),
      @Result(column = "height", property = "height"),
      @Result(column = "weight", property = "weight")
  })
  List<Child> selectMyPatients(@Param("doctorId") String doctorId, @Param("relationStatus") String relationStatus);

  @Select("SELECT * FROM u_children WHERE child_id IN "
          + "(SELECT child_id FROM d_doctors_children "
          + "WHERE doctor_id = #{doctorId} AND relation_status = #{relationStatus} "
          + "ORDER BY created_at DESC LIMIT 5)")
  @Results({
          @Result(column = "child_id", property = "childId"),
          @Result(column = "name", property = "name"),
          @Result(column = "school", property = "school"),
          @Result(column = "gender", property = "gender"),
          @Result(column = "birthdate", property = "birthdate"),
          @Result(column = "height", property = "height"),
          @Result(column = "weight", property = "weight")
  })
  List<Child> selectRecentPatients(@Param("doctorId") String doctorId, @Param("relationStatus") String relationStatus);

  @Insert("INSERT INTO d_doctors_children(doctor_id, child_id, relation_status, created_at) "
      + "VALUES(#{doctorId}, #{childId}, #{relationStatus}, #{createdAt})")
  @Options(useGeneratedKeys = true, keyProperty = "relationId")
  int createDoctorChildRelation(DoctorChildRelation relation);

  @Update("UPDATE d_doctors_children SET relation_status = #{relationStatus} "
      + "WHERE child_id = #{childId} AND doctor_id = #{doctorId}")
  boolean updateDoctorChildRelation(DoctorChildRelation relation);

  @Delete("DELETE FROM d_doctors_children WHERE doctor_id = #{doctorId} AND child_id = #{childId}")
  int deleteDoctorChildRelation(DoctorChildRelation relation);
}
package org.example.backend.mapper.doctor;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.doctor.DoctorChildRelation;
import org.example.backend.entity.user.Child;

@Mapper
public interface DoctorChildRelationMapper {
  @Select("SELECT * FROM u_children WHERE child_id IN "
      + "(SELECT child_id FROM d_doctors_children WHERE doctor_id = #{doctorId} ORDER BY created_at)")
  @Results({
      @Result(column = "child_id", property = "childId"),
      @Result(column = "name", property = "name"),
      @Result(column = "school", property = "school"),
      @Result(column = "gender", property = "gender"),
      @Result(column = "birthdate", property = "birthdate"),
      @Result(column = "height", property = "height"),
      @Result(column = "weight", property = "weight")
  })
  List<Child> selectMyPatients(String doctorId);

  @Insert("INSERT INTO d_doctors_children(relation_id, doctor_id, child_id, relation_type, created_at) "
      + "VALUES(#{relationId}, #{doctorId}, #{childId}, #{relationType}, #{createdAt})")
  int createDoctorChildRelation(DoctorChildRelation relation);

  @Delete("DELETE FROM d_doctors_children WHERE doctor_id = #{doctorId} AND child_id = #{childId}")
  int deleteDoctorChildRelation(DoctorChildRelation relation);
}
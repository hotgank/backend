package org.example.backend.mapper.user;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.backend.entity.user.Child;

@Mapper
public interface ChildMapper{

  // 查询所有孩子信息
  @Select("SELECT * FROM u_children")
  List<Child> selectAll();

  // 根据ID查询
  @Select("SELECT * FROM u_children WHERE child_id = #{childId}")
  Child selectById(String childId);

  // 插入孩子信息
  @Insert("INSERT INTO u_children(child_id, name, school, gender, birthdate, height, weight) "
      + "VALUES(#{childId}, #{name}, #{school}, #{gender}, #{birthdate}, #{height}, #{weight})")
  void insertChild(Child child);

  // 更新孩子信息
  @Update("UPDATE u_children SET name = #{name}, school = #{school}, " +
      "gender = #{gender}, birthdate = #{birthdate}, height = #{height}, "
      + "weight= #{weight} WHERE child_id = #{childId}")
  void updateChild(Child child);

  // 根据ID删除孩子信息
  @Delete("DELETE FROM u_children WHERE child_id = #{childId}")
  void deleteChild(String childId);
}
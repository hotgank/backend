package org.example.backend.mapper.user;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.user.ParentChildRelation;

/**
 *  ParentChildRelationMapper
 *  @author Q
 */
@Mapper
public interface ParentChildRelationMapper {
  //查询所有关系表中所有关系
  @Select("SELECT * FROM u_parents_children")
  @Results({
      @Result(column = "relation_id", property = "relationId"),
      @Result(column = "user_id", property = "userId"),
      @Result(column = "child_id", property = "childId"),
      @Result(column = "relation_type", property = "relationType"),
      @Result(column = "created_at", property = "createdAt")
  })
  List<ParentChildRelation> selectAll();

  //根据关系id查询
  @Select("SELECT * FROM u_parents_children WHERE relation_id = #{relationId}")
  @Results({
      @Result(column = "relation_id", property = "relationId"),
      @Result(column = "user_id", property = "userId"),
      @Result(column = "child_id", property = "childId"),
      @Result(column = "relation_type", property = "relationType"),
      @Result(column = "created_at", property = "createdAt")
  })
  ParentChildRelation selectById(@Param("relationId") int relationId);

  //根据userId查询
  @Select("SELECT * FROM u_parents_children WHERE user_id = #{userId}")
  @Results({
      @Result(column = "relation_id", property = "relationId"),
      @Result(column = "user_id", property = "userId"),
      @Result(column = "child_id", property = "childId"),
      @Result(column = "relation_type", property = "relationType"),
      @Result(column = "created_at", property = "createdAt")
 })
  List<ParentChildRelation> selectByUserId(@Param("userId") String userId);

  //插入新关系
  @Insert("INSERT INTO u_parents_children(relation_id, user_id, child_id, relation_type, created_at) "
      + "VALUES(#{relationId}, #{userId}, #{childId}, #{relationType}, #{createdAt})")

  void insertRelation(ParentChildRelation relation);
  // 更新关系
  @Insert("UPDATE u_parents_children SET "
      + "user_id = #{userId}, "
      + "child_id = #{childId}, "
      + "relation_type = #{relationType}, "
      + "created_at = #{createdAt} "
      + "WHERE relation_id = #{relationId}")
  void updateRelation(ParentChildRelation relation);

 // 删除关系
  @Insert("DELETE FROM u_parents_children WHERE relation_id = #{relationId}")
  void deleteRelationById(@Param("relationId") int relationId);

  //根据childId删除所有与childId有关的关系
  @Insert("DELETE FROM u_parents_children WHERE child_id = #{childId}")
  void deleteRelationsByChildId(@Param("childId") String childId);
}
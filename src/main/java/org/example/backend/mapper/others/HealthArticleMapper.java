package org.example.backend.mapper.others;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.example.backend.dto.HealthArticleDetailsDTO;
import org.example.backend.dto.HealthArticleTotalListDTO;
import org.example.backend.entity.others.HealthArticle;

@Mapper
public interface HealthArticleMapper {
  @Insert(
      "INSERT INTO o_health_articles(article_id,doctor_id,title,content,publish_date,type,status)"
          + "VALUES(#{articleId},#{doctorId},#{title},#{content},#{publishDate},#{type},#{status})")
  void insertHealthArticle(HealthArticle healthArticle);

  @Select("SELECT * FROM o_health_articles WHERE article_id =#{articleId}")
  @Results({
    @Result(column = "article_id", property = "articleId"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "title", property = "title"),
    @Result(column = "content", property = "content"),
    @Result(column = "publish_date", property = "publishDate"),
    @Result(column = "type", property = "type"),
    @Result(column = "status", property = "status"),
  })
  HealthArticle selectById(Integer articleId);

  @Select("SELECT ha.article_id, ha.title, ha.content, ha.publish_date, ha.type, ha.status, " +
          "d.name , d.username, d.gender, d.position, d.workplace, d.qualification, d.experience, d.avatar_url " +
          "FROM o_health_articles ha " +
          "JOIN d_doctors d ON ha.doctor_id = d.doctor_id " +
          "WHERE ha.article_id = #{articleId}")
  @Results({
          @Result(column = "article_id", property = "articleId"),
          @Result(column = "title", property = "title"),
          @Result(column = "content", property = "content"),
          @Result(column = "publish_date", property = "publishDate"),
          @Result(column = "type", property = "type"),
          @Result(column = "status", property = "status"),
          @Result(column = "name", property = "name"),
          @Result(column = "username", property = "username"),
          @Result(column = "gender", property = "gender"),
          @Result(column = "position", property = "position"),
          @Result(column = "workplace", property = "workplace"),
          @Result(column = "qualification", property = "qualification"),
          @Result(column = "experience", property = "experience"),
          @Result(column = "avatar_url", property = "avatarUrl"),
  })
  HealthArticleDetailsDTO selectDetailsById(Integer articleId);

  @Select("SELECT ha.article_id, d.name, ha.title, ha.content, ha.publish_date, ha.type, ha.status " +
          "FROM o_health_articles ha " +
          "JOIN d_doctors d ON ha.doctor_id = d.doctor_id " +
          "WHERE ha.status = '已发布'")
  @Results({
          @Result(column = "article_id", property = "articleId"),
          @Result(column = "name", property = "name"),
          @Result(column = "title", property = "title"),
          @Result(column = "publish_date", property = "publishDate"),
          @Result(column = "type", property = "type"),
          @Result(column = "status", property = "status"),
  })
  List<HealthArticleTotalListDTO> selectList();

  @Update(
      "UPDATE o_health_articles SET doctor_id=#{doctorId},title=#{title},content=#{content},publish_date=#{publishDate},type=#{type},status=#{status} WHERE article_id=#{articleId}")
  void updateById(HealthArticle healthArticle);

  @Delete("DELETE FROM o_health_articles WHERE article_id=#{articleId}")
  void deleteById(Integer articleId);

  @Select("SELECT * FROM o_health_articles WHERE doctor_id=#{doctorId}")
  @Results({
    @Result(column = "article_id", property = "articleId"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "title", property = "title"),
    @Result(column = "content", property = "content"),
    @Result(column = "publish_date", property = "publishDate"),
    @Result(column = "type", property = "type"),
    @Result(column = "status", property = "status"),
  })
  List<HealthArticle> selectByDoctorId(String doctorId);

  @Select("SELECT ha.article_id, d.name, ha.title, ha.content, ha.publish_date, ha.type, ha.status " +
          "FROM o_health_articles ha " +
          "JOIN d_doctors d ON ha.doctor_id = d.doctor_id " +
          "JOIN o_hospitals o ON d.workplace = o.hospital_name " +
          "JOIN a_admins a ON a.admin_id = #{adminId} " +
          "AND (" +
          "(a.admin_type = 'second' AND a.admin_id = o.admin_id) " +
          "OR (a.admin_type = 'first' AND d.workplace NOT IN " +
          "(SELECT h.hospital_name FROM o_hospitals h WHERE h.admin_id IS NOT NULL)) " +
          "OR (a.admin_type = 'super')" +
          ") "
  )
  @Results({
      @Result(column = "article_id", property = "articleId"),
      @Result(column = "name", property = "name"),
      @Result(column = "title", property = "title"),
      @Result(column = "publish_date", property = "publishDate"),
      @Result(column = "type", property = "type"),
      @Result(column = "status", property = "status"),
  })
  List<HealthArticleTotalListDTO> selectListAll(@Param("adminId") String adminId);


  @Select("SELECT COUNT(*) FROM o_health_articles WHERE doctor_id=#{doctorId} AND status='已发布'")
  int selectCountByDoctorId(String doctorId);
}

package org.example.backend.mapper.others;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

  @Select("SELECT * FROM o_health_articles WHERE status = '已发布'")
  @Results({
    @Result(column = "article_id", property = "articleId"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "title", property = "title"),
    @Result(column = "content", property = "content"),
    @Result(column = "publish_date", property = "publishDate"),
    @Result(column = "type", property = "type"),
    @Result(column = "status", property = "status"),
  })
  List<HealthArticle> selectList();

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

  @Select("SELECT * FROM o_health_articles")
  @Results({
      @Result(column = "article_id", property = "articleId"),
      @Result(column = "doctor_id", property = "doctorId"),
      @Result(column = "title", property = "title"),
      @Result(column = "content", property = "content"),
      @Result(column = "publish_date", property = "publishDate"),
      @Result(column = "type", property = "type"),
      @Result(column = "status", property = "status"),
  })
  List<HealthArticle> selectListAll();


  @Select("SELECT COUNT(*) FROM o_health_articles WHERE doctor_id=#{doctorId} AND status='已发布'")
  int selectCountByDoctorId(String doctorId);
}

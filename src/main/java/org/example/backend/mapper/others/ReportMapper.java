package org.example.backend.mapper.others;

import org.apache.ibatis.annotations.*;
import org.example.backend.dto.DoctorGetReportDTO;
import org.example.backend.entity.others.Report;

import java.util.List;

@Mapper
public interface ReportMapper {
  @Select("SELECT * FROM r_reports WHERE child_id = #{childId} ORDER BY created_at DESC")
  @Results({
    @Result(column = "report_id", property = "reportId"),
    @Result(column = "child_id", property = "childId"),
    @Result(column = "created_at", property = "createdAt"),
    @Result(column = "report_type", property = "reportType"),
    @Result(column = "state", property = "state"),
    @Result(column = "result", property = "result"),
    @Result(column = "analyse", property = "analyse"),
    @Result(column = "comment", property = "comment"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "url", property = "url"),
      @Result(column = "allow_state", property = "allowState"),
      @Result(column = "read_state", property = "readState")
  })
  List<Report> selectByChildId(@Param("childId") String childId);

  @Select("SELECT * FROM r_reports WHERE report_id = #{reportId} ORDER BY created_at DESC")
  @Results({
    @Result(column = "report_id", property = "reportId"),
    @Result(column = "child_id", property = "childId"),
    @Result(column = "created_at", property = "createdAt"),
    @Result(column = "report_type", property = "reportType"),
    @Result(column = "state", property = "state"),
    @Result(column = "result", property = "result"),
    @Result(column = "analyse", property = "analyse"),
    @Result(column = "comment", property = "comment"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "url", property = "url")
  })
  Report selectByReportId(@Param("reportId") int reportId);

  @Insert(
      "INSERT INTO r_reports (child_id, created_at, report_type, state, result, analyse, comment, doctor_id, url)"
          + "VALUES (#{childId}, #{createdAt}, #{reportType}, #{state}, #{result}, #{analyse}, #{comment}, #{doctorId}, #{url})")
  @Options(useGeneratedKeys = true, keyProperty = "reportId", keyColumn = "report_id")
  int insert(Report report);

  @Update(
      "UPDATE r_reports SET "
          + "child_id = #{childId}, "
          + "created_at = #{createdAt}, "
          + "report_type = #{reportType}, "
          + "state = #{state}, "
          + "result = #{result}, "
          + "analyse = #{analyse}, "
          + "comment = #{comment}, "
          + "doctor_id = #{doctorId}, "
          + "url = #{url} "
          + "WHERE report_id = #{reportId}")
  void update(Report report);

  // 根据childId删除报告
  @Delete("DELETE FROM r_reports WHERE child_id = #{childId}")
  void deleteByChildId(@Param("childId") String childId);

  // 根据reportId删除报告
  @Delete("DELETE FROM r_reports WHERE report_id = #{reportId}")
  void deleteByReportId(@Param("reportId") int reportId);

  // 查询所有报告
  @Select("SELECT * FROM r_reports")
  @Results({
    @Result(column = "report_id", property = "reportId"),
    @Result(column = "child_id", property = "childId"),
    @Result(column = "created_at", property = "createdAt"),
    @Result(column = "report_type", property = "reportType"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "url", property = "url"),
    @Result(column = "state", property = "state"),
    @Result(column = "result", property = "result"),
    @Result(column = "analyse", property = "analyse"),
    @Result(column = "comment", property = "comment"),
  })
  List<Report> selectAll();

  @Select(
      "SELECT * FROM r_reports WHERE child_id IN (SELECT child_id FROM u_parents_children WHERE user_id = #{userId}) ORDER BY created_at DESC")
  @Results({
    @Result(column = "report_id", property = "reportId"),
    @Result(column = "child_id", property = "childId"),
    @Result(column = "created_at", property = "createdAt"),
    @Result(column = "report_type", property = "reportType"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "url", property = "url"),
    @Result(column = "name", property = "name"),
    @Result(column = "gender", property = "gender"),
    @Result(column = "birthdate", property = "birthdate"),
    @Result(column = "weight", property = "weight"),
    @Result(column = "height", property = "height")
  })
  List<Report> selectByUserId(@Param("userId") String userId);

  // 多表查询，查询u_parents_children表和r_reports表和u_children，根据userId查询出u_parents_children中的关系，根据所获得的所有关系的childId查询出r_reports表中allow_state为“allow”的报告，根据childId查询出u_children表中的信息，返回一个DoctorGetReportDTO对象
  @Select(
      "SELECT r.report_id, r.child_id, r.created_at, r.report_type, r.doctor_id, r.url,r.state,r.result,r.analyse,r.comment, u.name, u.gender, u.birthdate, u.weight, u.height "
          + "FROM r_reports r "
          + "JOIN u_parents_children p ON r.child_id = p.child_id "
          + "JOIN u_children u ON r.child_id = u.child_id "
          + "WHERE p.user_id = #{userId} AND r.allow_state = 'allow'"
          + "ORDER BY r.created_at DESC")
  @Results({
    @Result(column = "report_id", property = "reportId"),
    @Result(column = "child_id", property = "childId"),
    @Result(column = "created_at", property = "createdAt"),
    @Result(column = "report_type", property = "reportType"),
    @Result(column = "doctor_id", property = "doctorId"),
    @Result(column = "url", property = "url"),
    @Result(column = "name", property = "name"),
    @Result(column = "gender", property = "gender"),
    @Result(column = "birthdate", property = "birthdate"),
    @Result(column = "weight", property = "weight"),
    @Result(column = "height", property = "height"),
    @Result(column = "state", property = "state"),
    @Result(column = "result", property = "result"),
    @Result(column = "analyse", property = "analyse"),
    @Result(column = "comment", property = "comment")
  })
  List<DoctorGetReportDTO> selectUserHistoryReport(@Param("userId") String userId);

  //授权接口
  @Update(
      "UPDATE r_reports SET allow_state = #{allowState} WHERE report_id = #{reportId}")
  boolean editReportAllowState(@Param("reportId") int reportId, @Param("allowState") String allowState);

  //根据userId查询出u_parents_children表中的所有childId，然后根据childId查询计算出r_reports表中read_state为"unread"的个数
  @Select(
      "SELECT COUNT(r.report_id) "
          + "FROM r_reports r "
          + "JOIN u_parents_children p ON r.child_id = p.child_id "
          + "WHERE p.user_id = #{userId} AND r.read_state = 'unread'")
  int countUnreadReports(@Param("userId") String userId);

  //根据childId查询，把read_state为"unread"的改为"read"
  @Update(
      "UPDATE r_reports SET read_state = 'read' WHERE child_id = #{childId} AND read_state = 'unread'")
  boolean updateReadStateByChildId(@Param("childId") String childId);
}

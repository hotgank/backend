package org.example.backend.mapper.others;

import org.apache.ibatis.annotations.*;
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
            @Result(column = "url", property = "url")
    })
    List <Report> selectByChildId(@Param("childId") String childId);

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

    @Insert("INSERT INTO r_reports (child_id, created_at, report_type, state, result, analyse, comment, doctor_id, url)"
            + "VALUES (#{childId}, #{createdAt}, #{reportType}, #{state}, #{result}, #{analyse}, #{comment}, #{doctorId}, #{url})")
    @Options(useGeneratedKeys = true, keyProperty = "reportId", keyColumn = "report_id")
    int insert(Report report);

    @Update("UPDATE r_reports SET "
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

    //根据childId删除报告
    @Delete("DELETE FROM r_reports WHERE child_id = #{childId}")
    void deleteByChildId(@Param("childId") String childId);

    //根据reportId删除报告
    @Delete("DELETE FROM r_reports WHERE report_id = #{reportId}")
    void deleteByReportId(@Param("reportId") int reportId);

    //查询所有报告
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
}
package org.example.backend.service.others;

import org.example.backend.dto.DoctorGetReportDTO;
import org.example.backend.dto.UserHistoryReportDTO;
import org.example.backend.entity.others.Report;

import java.util.List;

public interface ReportService {

  Report selectByReportId(int reportId);

  List<Report> selectByChildId(String childId);

  List<UserHistoryReportDTO> selectUserHistoryReport(String userId);

  int insertReport(Report report);

  boolean updateReport(Report report);

  boolean deleteByChildId(String childId);

  boolean deleteByReportId(int reportId);

  List<Report> selectAll();

  List<Report> selectByUserId(String userId);

  // 医生编辑报告
  boolean doctorEditReport(int reportId, String comment, String doctorId);

  // 医生获取报告
  List<DoctorGetReportDTO> DoctorGetReportByUserId(String userId);

  //授权接口
  boolean allowReport(int reportId, String allowState);

  //获取用户未读报告的数量
  int countUnreadReports(String userId);

  //更新报告已读状态
  boolean updateReadStateByChildId(String childId);
}

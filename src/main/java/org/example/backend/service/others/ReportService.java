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
}

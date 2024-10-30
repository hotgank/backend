package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.Report;

public interface ReportService {
  List<Report> getAllReports();
  Report getReportById(int id);
  int createReport(Report report);
  int updateReport(Report report);
  int deleteReportById(int id);
}

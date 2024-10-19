package org.example.backend.Service.User;

import java.util.List;
import org.example.backend.Entity.User.Report;

public interface ReportService {
  List<Report> getAllReports();
  Report getReportById(int id);
  int createReport(Report report);
  int updateReport(Report report);
  int deleteReportById(int id);
}

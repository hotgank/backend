package org.example.backend.service.others;

import org.example.backend.entity.user.Report;

import java.util.List;

public interface ReportService {

    int insertReport(Report report);

    Report selectByReportId(String reportId);

    List<Report> selectByChildId(String childId);

    boolean updateReport(Report report);
}

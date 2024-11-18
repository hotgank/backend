package org.example.backend.service.others;

import org.example.backend.entity.others.Report;

import java.util.List;

public interface ReportService {


    Report selectByReportId(int reportId);

    List<Report> selectByChildId(String childId);

    int insertReport(Report report);

    boolean updateReport(Report report);

    boolean deleteByChildId(String childId);

    boolean deleteByReportId(int reportId);

    List<Report> selectAll();

    List<Report> selectByUserId(String userId);
}

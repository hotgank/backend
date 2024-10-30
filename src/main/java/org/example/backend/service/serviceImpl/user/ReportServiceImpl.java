package org.example.backend.service.serviceImpl.user;

import java.util.List;
import org.example.backend.entity.user.Report;
import org.example.backend.mapper.user.ReportMapper;
import org.example.backend.service.user.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
  @Autowired
  private ReportMapper reportMapper;

  @Override
  public List<Report> getAllReports() {
    return reportMapper.selectList(null);
  }

  @Override
  public Report getReportById(int id) {
    return reportMapper.selectById(id);
  }

  @Override
  public int createReport(Report report) {
    return reportMapper.insert(report);
  }

  @Override
  public int updateReport(Report report) {
    return reportMapper.updateById(report);
  }

  @Override
  public int deleteReportById(int id) {
    return reportMapper.deleteById(id);
  }
}

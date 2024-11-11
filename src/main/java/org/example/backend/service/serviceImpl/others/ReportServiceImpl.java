package org.example.backend.service.serviceImpl.others;

import java.time.LocalDateTime;
import java.util.List;

import org.example.backend.entity.others.Report;
import org.example.backend.mapper.others.ReportMapper;
import org.example.backend.service.others.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

  private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

  @Autowired
  private ReportMapper reportMapper;

  @Override
  public Report selectByReportId(String reportId) {
    try {
      return reportMapper.selectByReportId(reportId);
    }
    catch (Exception e) {
      // 记录异常日志
      logger.error("获取报告失败, reportId: {}", reportId, e);
      return null;
    }
  }

  @Override
  public List <Report> selectByChildId(String childId) {
    try {
      return reportMapper.selectByChildId(childId);
    }
    catch (Exception e) {
      // 记录异常日志
      logger.error("获取报告失败, childId: {}", childId, e);
      return null;
    }
  }

  @Override
  public int insertReport(Report report) {
    try {
      report.setCreatedAt(LocalDateTime.now());
      return reportMapper.insert(report);
    }
    catch (Exception e) {
      // 记录异常日志
      logger.error("保存报告失败, childId: {}", report.getChildId(), e);
      return 0;
    }
  }

  @Override
  public boolean updateReport(Report report) {
    try {
      reportMapper.update(report);
      return true;
    }
    catch (Exception e) {
      // 记录异常日志
      logger.error("更新报告失败, reportId: {}", report.getReportId(), e);
      return false;
    }
  }
}

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
  public Report selectByReportId(int reportId) {
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
      reportMapper.insert(report);
      return reportMapper.selectAll().size();
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

  @Override
  public boolean deleteByChildId(String childId) {
    try {
      reportMapper.deleteByChildId(childId);
      return true;
    }
    catch (Exception e) {
      // 记录异常日志
      logger.error("删除报告失败, childId: {}", childId, e);
      return false;
    }
  }

  @Override
  public boolean deleteByReportId(int reportId) {
    try {
      reportMapper.deleteByReportId(reportId);
      return true;
    }
    catch (Exception e) {
      // 记录异常日志
      logger.error("删除报告失败, reportId: {}", reportId, e);
      return false;
    }
  }

  @Override
  public List<Report> selectAll() {
    try {
      return reportMapper.selectAll();
    }
    catch (Exception e) {
      // 记录异常日志
      logger.error("获取所有报告失败", e);
      return null;
    }
  }
}

package org.example.backend.service.serviceImpl.others;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.example.backend.dto.DoctorGetReportDTO;
import org.example.backend.dto.UserHistoryReportDTO;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.others.Report;
import org.example.backend.entity.user.Child;
import org.example.backend.entity.user.ParentChildRelation;
import org.example.backend.mapper.others.ReportMapper;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.service.others.ReportService;
import org.example.backend.service.user.ChildService;
import org.example.backend.service.user.ParentChildRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

  private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

  @Autowired
  private ReportMapper reportMapper;

  @Autowired
  private ParentChildRelationService parentChildRelationService;

  @Autowired
  private ChildService childService;

  @Autowired
  private DoctorService doctorService;

  @Override
  public List<UserHistoryReportDTO> selectUserHistoryReport(String userId) {
    List<ParentChildRelation> childRelations = parentChildRelationService.getRelationsByUserId(userId);

    // Step 2: 遍历孩子，获取每个孩子的详细信息和报告
    List<UserHistoryReportDTO> allReports = new ArrayList<>();
    for (ParentChildRelation relation : childRelations) {
        // 获取孩子基本信息
        Child child = childService.selectById(relation.getChildId());

        // 获取该孩子的所有报告
        List<Report> reports = this.selectByChildId(relation.getChildId());

        // 整合数据
        for (Report report : reports) {
            UserHistoryReportDTO reportDTO = new UserHistoryReportDTO();
            reportDTO.setId(report.getReportId());
            reportDTO.setChildName(child.getName());
            reportDTO.setReportType(report.getReportType());
            reportDTO.setCreatedAt(report.getCreatedAt());
            reportDTO.setState(report.getState());
            reportDTO.setResult(report.getResult());
            reportDTO.setComment(report.getComment());
            reportDTO.setAnalyse(report.getAnalyse());
            reportDTO.setUrl(report.getUrl());
            allReports.add(reportDTO);
        }
    }

    // Step 3: 按创建时间降序排序
    allReports.sort((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()));

    return allReports;
  }
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
      return report.getReportId();
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

  @Override
  public List<Report> selectByUserId(String userId) {
    try {
      return reportMapper.selectByUserId(userId);
    }catch (Exception e) {
      // 记录异常日志
      logger.error("获取用户报告失败, userId: {}", userId, e);
      return null;
    }
  }

  @Override
  public boolean doctorEditReport(int reportId, String comment, String doctorId) {
    try {
      //根据reportId获取报告
      Report report = selectByReportId(reportId);
      //获取报告的医生id，查询医生信息
      Doctor doctor = doctorService.selectById(doctorId);
      String doctorName = doctor.getName();
      String doctorPosition = doctor.getPosition();
      String doctorWorkplace = doctor.getWorkplace();
      //构造医生信息字符串
      String doctorInfo = "-----" + doctorName + "，" + doctorPosition + "，" + doctorWorkplace;
      //如果评论不为空，检查医生信息，如果医生信息为空，则添加；如果医生信息不为空，则覆盖更新
      if (comment != null){
        //看comment是否有"-----"
        if (comment.contains("-----")){
          //如果有且医生信息符合，则不添加，否则覆盖添加
          if (!comment.contains(doctorInfo)){
            //覆盖"-----"以及之后的内容
            comment = comment.replaceFirst("-----.*", doctorInfo);
          }
        }
        else {
          comment = comment + doctorInfo;
        }
      }
      report.setComment(comment);
      report.setDoctorId(doctorId);
      report.setCreatedAt(LocalDateTime.now());
      //如果报告类型不是以"评估报告"结尾，则添加"评估报告"
      if (!report.getReportType().endsWith("评估报告")) {
        report.setReportType(report.getReportType() + "评估报告");
      }
      reportMapper.update(report);
      return true;
    }
    catch (Exception e) {
      // 记录异常日志
      logger.error("医生编辑报告失败, reportId: {}", reportId, e);
      return false;
    }
  }

  @Override
  public List<DoctorGetReportDTO> DoctorGetReportByUserId(String userId) {
    try {
      return reportMapper.selectUserHistoryReport(userId);
    }
    catch (Exception e) {
      // 记录异常日志
      logger.error("获取用户报告失败, userId: {}", userId, e);
      return null;
    }
  }
}

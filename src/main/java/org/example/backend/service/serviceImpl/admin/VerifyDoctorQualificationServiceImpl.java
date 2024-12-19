package org.example.backend.service.serviceImpl.admin;

import org.example.backend.dto.AdminGetDoctorLicenseDTO;
import org.example.backend.entity.admin.LicenseCheck;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.mapper.admin.LicenseCheckMapper;
import org.example.backend.mapper.doctor.DoctorMapper;
import org.example.backend.service.admin.VerifyDoctorQualificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VerifyDoctorQualificationServiceImpl implements VerifyDoctorQualificationService {

  private static final Logger logger =
      LoggerFactory.getLogger(VerifyDoctorQualificationServiceImpl.class);

  @Autowired LicenseCheckMapper licenseCheckMapper;

  @Autowired DoctorMapper doctorMapper;

  @Override
  public int selectPendingCount(String adminId) {
    try {
      return licenseCheckMapper.selectPendingCount(adminId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取未通过审核医生数量失败", e);
      return 0;
    }
  }

  @Override
  public List<AdminGetDoctorLicenseDTO> selectAll(String adminId) {
    try {
      return licenseCheckMapper.adminSelectAll(adminId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取所有审核信息失败", e);
      return null;
    }
  }

  @Override
  public List<AdminGetDoctorLicenseDTO> selectRecent(String adminId) {
    try {
      return licenseCheckMapper.adminSelectRecent(adminId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取所有审核信息失败", e);
      return null;
    }
  }

  @Override
  public boolean approve(String auditId, String adminId, String position) {
    try {
      LicenseCheck licenseCheck = licenseCheckMapper.selectById(auditId);
      Doctor doctor = doctorMapper.selectById(licenseCheck.getDoctorId());
      if (doctor != null) {
        doctor.setPosition(position);
        doctor.setQualification("已认证");
        doctorMapper.updateDoctor(doctor);
      } else {
        logger.error("审核通过失败");
        return false;
      }
      licenseCheck.setAdminId(adminId);
      licenseCheck.setStatus("认证通过");
      licenseCheck.setUpdatedAt(LocalDateTime.now());
      licenseCheck.setComment("");
      return licenseCheckMapper.update(licenseCheck);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("审核通过失败", e);
      return false;
    }
  }

  @Override
  public boolean reject(String auditId, String adminId, String comment) {
    try {
      LicenseCheck licenseCheck = licenseCheckMapper.selectById(auditId);
      licenseCheck.setAdminId(adminId);
      licenseCheck.setStatus("已打回");
      licenseCheck.setUpdatedAt(LocalDateTime.now());
      licenseCheck.setComment(comment);
      return licenseCheckMapper.update(licenseCheck);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("审核拒绝失败", e);
      return false;
    }
  }
}

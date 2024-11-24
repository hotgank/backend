package org.example.backend.service.serviceImpl.admin;

import org.example.backend.dto.AdminGetDoctorLicenseDTO;
import org.example.backend.entity.admin.LicenseCheck;
import org.example.backend.mapper.admin.LicenseCheckMapper;
import org.example.backend.service.admin.VerifyDoctorQualificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VerifyDoctorQualificationServiceImpl implements VerifyDoctorQualificationService {

    private static final Logger logger = LoggerFactory.getLogger(VerifyDoctorQualificationServiceImpl.class);

    @Autowired
    LicenseCheckMapper licenseCheckMapper;

    @Override
    public List<AdminGetDoctorLicenseDTO> selectAll() {
        try {
            return licenseCheckMapper.adminSelectAll();
        }
        catch (Exception e) {
            // 记录异常日志
            logger.error("获取所有审核信息失败", e);
            return null;
        }
    }

    @Override
    public boolean approve(String auditId, String adminId) {
        try {
            LicenseCheck licenseCheck = licenseCheckMapper.selectById(auditId);
            licenseCheck.setAdminId(adminId);
            licenseCheck.setStatus("approved");
            licenseCheck.setUpdatedAt(LocalDateTime.now());
            licenseCheck.setComment("");
            return licenseCheckMapper.update(licenseCheck);
        }
        catch (Exception e) {
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
            licenseCheck.setStatus("rejected");
            licenseCheck.setUpdatedAt(LocalDateTime.now());
            licenseCheck.setComment(comment);
            return licenseCheckMapper.update(licenseCheck);
        }
        catch (Exception e) {
            // 记录异常日志
            logger.error("审核拒绝失败", e);
            return false;
        }
    }
}

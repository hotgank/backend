package org.example.backend.service.serviceImpl.admin;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import org.example.backend.entity.admin.Admin;
import org.example.backend.mapper.admin.AdminMapper;
import org.example.backend.service.admin.AdminService;
import org.example.backend.util.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

  private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

  @Autowired
  private AdminMapper adminMapper;

  @Override
  public Admin selectById(String adminId) {
    try {
      return adminMapper.selectById(adminId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取管理员信息失败，adminId: {}", adminId, e);
      return null;
    }
  }

  @Override
  public List<Admin> selectAll() {
    try {
      return adminMapper.selectAll();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取所有管理员失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public String insert(Admin admin) {
    try {
      String adminId = "A-" + UUID.randomUUID();
      admin.setAdminId(adminId);
      String username = EncryptionUtil.encryptMD5(admin.getUsername());
      admin.setUsername(username);
      String password = EncryptionUtil.encryptMD5(admin.getPassword());
      admin.setPassword(password);
      admin.setRegistrationDate(LocalDateTime.now());
      admin.setStatus("active");
      adminMapper.insertAdmin(admin);
      logger.info("Admin with ID {} inserted successfully", admin.getAdminId());
      return adminId;
    } catch (Exception e) {
      logger.error("Error inserting admin with ID {}: {}", admin.getAdminId(), e.getMessage(), e);
      return null;
    }
  }

  @Override
  public boolean update(Admin admin) {
    try {
      adminMapper.updateAdmin(admin);
      logger.info("Admin with ID {} updated successfully", admin.getAdminId());
      return true;
    } catch (Exception e) {
      logger.error("Error updating admin with ID {}: {}", admin.getAdminId(), e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean delete(String adminId) {
    try {
      adminMapper.deleteAdmin(adminId);
      logger.info("Admin with ID {} deleted successfully", adminId);
      return true;
    } catch (Exception e) {
      logger.error("Error deleting admin with ID {}: {}", adminId, e.getMessage(), e);
      return false;
    }
  }
}

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

  @Autowired private AdminMapper adminMapper;

  @Autowired private EncryptionUtil encryptionUtil;

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
  public List<Admin> selectSecondAdmins() {
    try {
      return adminMapper.selectSecondAdmins();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取所有管理员失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public String insert(Admin admin) {
    try {
      Admin selectedAdmin;
      selectedAdmin = adminMapper.selectAdminByUsername(admin.getUsername());
      if(selectedAdmin != null){
        logger.info("管理员用户名 {} 已存在", admin.getUsername());
        return null;
      }
      selectedAdmin = adminMapper.selectAdminByEmail(admin.getEmail());
      if(selectedAdmin != null){
        logger.info("管理员邮箱 {} 已存在", admin.getEmail());
        return null;
      }
      String password = encryptionUtil.encryptMD5(admin.getPassword());
      admin.setPassword(password);
      admin.setRegistrationDate(LocalDateTime.now());
      admin.setStatus("active");
      String adminId = "A-" + UUID.randomUUID();
      admin.setAdminId(adminId);
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
  public boolean updateMyEmailAndPhone(String adminId, String email, String phone) {
    try {
      Admin admin = selectById(adminId);
      admin.setEmail(email);
      admin.setPhone(phone);
      adminMapper.updateAdmin(admin);
      logger.info("Admin email and phone with ID {} updated successfully", admin.getAdminId());
      return true;
    } catch (Exception e) {
      logger.error("Error updating admin with ID {}: {}", adminId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean updateMyPassword(String adminId, String password) {
    try {
      Admin admin = selectById(adminId);
      password = encryptionUtil.encryptMD5(password);
      admin.setPassword(password);
      adminMapper.updateAdmin(admin);
      logger.info("Admin password with ID {} updated successfully", admin.getAdminId());
      return true;
    } catch (Exception e) {
      logger.error("Error updating admin with ID {}: {}", adminId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean activateAdmin(String adminId) {
    try {
      Admin admin = selectById(adminId);
      admin.setStatus("active");
      adminMapper.updateAdmin(admin);
      logger.info("Admin with ID {} activated successfully", admin.getAdminId());
      return true;
    } catch (Exception e) {
      logger.error("Error activating admin with ID {}: {}", adminId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean banAdmin(String adminId) {
    try {
      Admin admin = selectById(adminId);
      admin.setStatus("disabled");
      adminMapper.updateAdmin(admin);
      logger.info("Admin with ID {} banned successfully", admin.getAdminId());
      return true;
    } catch (Exception e) {
      logger.error("Error banning admin with ID {}: {}", adminId, e.getMessage(), e);
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

  @Override
  public String verifyByUsernameAndPassword(String username, String password) {
    Admin adminDetails = adminMapper.selectAdminByUsername(username);
    if (adminDetails == null) {
      return null;
    }
    if (adminDetails.getStatus().equals("disabled")) {
      return "disabled";
    }
    if (encryptionUtil.verifyMD5(password, adminDetails.getPassword())) {
      return adminDetails.getAdminId();
    }
    return null;
  }

  @Override
  public String verifyByEmailAndPassword(String email, String password) {
    Admin adminDetails = adminMapper.selectAdminByEmail(email);
    if (adminDetails == null) {
      return null;
    }
    if (adminDetails.getStatus().equals("disabled")) {
      return "disabled";
    }
    if (encryptionUtil.verifyMD5(password, adminDetails.getPassword())) {
      return adminDetails.getAdminId();
    }
    return null;
  }

  @Override
  public boolean verifyByIdAndPassword(String adminId, String password) {
    Admin admin = adminMapper.selectById(adminId);
    return encryptionUtil.verifyMD5(password, admin.getPassword());
  }
}

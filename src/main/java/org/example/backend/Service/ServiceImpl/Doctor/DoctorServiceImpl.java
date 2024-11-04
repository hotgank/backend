package org.example.backend.service.serviceImpl.doctor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.mapper.doctor.DoctorMapper;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.util.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

  private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

  @Autowired
  private DoctorMapper doctorMapper;

  @Override
  public Doctor selectById(String doctorId) {
    try {
      return doctorMapper.selectById(doctorId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取医生信息失败，doctorId: {}", doctorId, e);
      return null;
    }
  }

  @Override
  public List<Doctor> selectAll() {
    try {
      return doctorMapper.selectAll();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取所有医生失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public String insert(Doctor doctor) {
    try {
      String doctorId = "D-" + UUID.randomUUID();
      doctor.setDoctorId(doctorId);
      String password = EncryptionUtil.encryptMD5(doctor.getPassword());
      doctor.setPassword(password);
      doctor.setRating(-1);
      doctor.setStatus("active");
      doctor.setRegistrationDate(LocalDateTime.now());
      doctorMapper.insertDoctor(doctor);
      logger.info("Doctor with ID {} inserted successfully", doctor.getDoctorId());
      return doctorId;
    } catch (Exception e) {
      logger.error("Error inserting doctor with ID {}: {}", doctor.getDoctorId(), e.getMessage(), e);
      return null;
    }
  }

  @Override
  public boolean update(Doctor doctor) {
    try {
      doctorMapper.updateDoctor(doctor);
      logger.info("Doctor with ID {} updated successfully", doctor.getDoctorId());
      return true;
    } catch (Exception e) {
      logger.error("Error updating doctor with ID {}: {}", doctor.getDoctorId(), e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean delete(String doctorId) {
    try {
      doctorMapper.deleteDoctor(doctorId);
      logger.info("Doctor with ID {} deleted successfully", doctorId);
      return true;
    } catch (Exception e) {
      logger.error("Error deleting child with ID {}: {}", doctorId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public String generateRegisterCode(String email) {
    // 生成一个唯一的注册码
    String registerCode = UUID.randomUUID().toString().substring(0, 8);
    // 存储注册码到数据库或其他存储方式
    // 这里假设有一个方法可以存储注册码，例如：
    // registerCodeRepository.save(new RegisterCode(email, registerCode));
    return registerCode;
  }

  @Override
  public boolean registerDoctor(Doctor doctor) {
//    // 检查注册码是否有效
//    String email = request.getEmail();
//    String registerCode = request.getRegisterCode();
//    // 假设有一个方法可以验证注册码，例如：
//    // boolean isValid = registerCodeRepository.isValid(email, registerCode);
//    boolean isValid = true; // 这里假设注册码总是有效的，实际应用中需要验证
//
//    if (!isValid) {
      return false;
//    }
//
//    // 创建医生对象
//    Doctor doctor = new Doctor();
//    doctor.setName(request.getName());
//    doctor.setEmail(email);
//    doctor.setPassword(request.getPassword());
//    doctor.setRegisterCode(registerCode);
//
//    // 插入医生信息到数据库
//    return createDoctor(doctor);
  }
}

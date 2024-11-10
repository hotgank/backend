package org.example.backend.service.serviceImpl.doctor;

import jakarta.annotation.Resource;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DoctorServiceImpl implements DoctorService {

  private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);
  @Resource
  private RedisTemplate<String, String> redisTemplate;
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
  public boolean updatePassword(String doctorId, String newPassword) {
    try {
      Doctor doctor = selectById(doctorId);
      if (doctor == null) {
        return false;
      }
      String encryptedPassword = EncryptionUtil.encryptMD5(newPassword);
      doctor.setPassword(encryptedPassword);
      update(doctor);
      return true;
    } catch (Exception e) {
      logger.error("Error updating password for doctor with ID {}:{}", doctorId, e.getMessage(), e);
    }
    return false;
  }

  @Override
  public boolean validatePassword(String doctorId, String password){
    Doctor doctor = selectById(doctorId);
    if(doctor == null){
      return false;
    }
    if(EncryptionUtil.verifyMD5(password, doctor.getPassword())){
      return true;
    }else{
      return false;
    }
  }

  @Override
  public boolean validateRegisterCode(String email, String registerCode) {
    String storedRegisterCode = redisTemplate.opsForValue().get(email);
    if (storedRegisterCode == null || !storedRegisterCode.equals(registerCode)) {
      return false;
    }
    return true;
  }

  @Override
  public String generateRegisterCode(String email) {
    // 检查Redis中是否已存在该邮箱的验证码
    if (redisTemplate.hasKey(email)) {
      // 如果存在，则删除旧的验证码
      redisTemplate.delete(email);
    }

    // 生成一个唯一的注册码
    String registerCode = UUID.randomUUID().toString().substring(0, 8);

    // 将新的验证码存储到Redis中，设置过期时间为5分钟
    redisTemplate.opsForValue().set(email, registerCode, 5, TimeUnit.MINUTES);

    return registerCode;
  }

  @Override
  public boolean registerDoctor(Doctor doctor) {
    // 检查注册码是否有效
    String email = doctor.getEmail();


    // 验证注册码是否有效


    // 创建医生对象
    doctor.setPassword(EncryptionUtil.encryptMD5(doctor.getPassword()));
    doctor.setRating(-1);
    doctor.setStatus("active");
    doctor.setRegistrationDate(LocalDateTime.now());

    // 插入医生信息到数据库
    return insert(doctor) != null;
  }
}

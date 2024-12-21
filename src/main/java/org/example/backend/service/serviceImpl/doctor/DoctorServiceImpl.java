package org.example.backend.service.serviceImpl.doctor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.util.*;

import org.example.backend.entity.doctor.Doctor;
import org.example.backend.mapper.doctor.DoctorMapper;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.util.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DoctorServiceImpl implements DoctorService {

  private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

  @Resource private RedisTemplate<String, String> redisTemplate;

  @Autowired private DoctorMapper doctorMapper;

  @Autowired private EncryptionUtil encryptionUtil;

  @Override
  public String getDoctorAvatar(String doctorId) {
    String avatarBase64 = doctorMapper.getAvatarUrl(doctorId);
    if (avatarBase64 != null && avatarBase64.startsWith("http://localhost:8080")) {
      avatarBase64 = avatarBase64.replace("http://localhost:8080", "https://zeropw.cn:8081");
    }
    return avatarBase64;
  }


  @Override
  public int selectDoctorCount() {
    try {
      return doctorMapper.selectDoctorCount();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取医生数量失败", e);
      return 0;
    }
  }

  @Override
  public int selectMyDoctorCount(String adminId) {
    try {
      return doctorMapper.selectMyDoctorCount(adminId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取医生数量失败", e);
      return 0;
    }
  }

  @Override
  public Doctor selectDoctorByEmail(String email) {
    return doctorMapper.selectDoctorByEmail(email);
  }

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
            String password = encryptionUtil.encryptMD5(doctor.getPassword());
            doctor.setPassword(password);
            doctor.setRating(-1);
            doctor.setStatus("active");
            doctor.setRegistrationDate(LocalDateTime.now());
            doctorMapper.insertDoctor(doctor);
            logger.info("Doctor with ID {} inserted successfully", doctor.getDoctorId());
            return doctorId;
        } catch (Exception e) {
            logger.error(
                    "Error inserting doctor with ID {}: {}", doctor.getDoctorId(), e.getMessage(), e);
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
  public boolean banAccount(String doctorId) {
    try {
      Doctor doctor = selectById(doctorId);
      if (doctor == null) {
        return false;
      }
      doctor.setStatus("disabled");
      update(doctor);
      return true;
    } catch (Exception e) {
      logger.error("Error banning doctor with ID {}: {}", doctorId, e.getMessage(), e);
    }
    return false;
  }

  @Override
  public boolean activeAccount(String doctorId) {
    try {
      Doctor doctor = selectById(doctorId);
      if (doctor == null) {
        return false;
      }
      doctor.setStatus("active");
      update(doctor);
      return true;
    } catch (Exception e) {
      logger.error("Error active doctor with ID {}: {}", doctorId, e.getMessage(), e);
    }
    return false;
  }

  @Override
  public boolean updatePassword(String doctorId, String newPassword) {
    try {
      Doctor doctor = selectById(doctorId);
      if (doctor == null) {
        return false;
      }
      String encryptedPassword = encryptionUtil.encryptMD5(newPassword);
      doctor.setPassword(encryptedPassword);
      update(doctor);
      return true;
    } catch (Exception e) {
      logger.error("Error updating password for doctor with ID {}:{}", doctorId, e.getMessage(), e);
    }
    return false;
  }

  @Override
  public boolean validatePassword(String doctorId, String password) {
    Doctor doctor = selectById(doctorId);
    if (doctor == null) {
      return false;
    }
    if (encryptionUtil.verifyMD5(password, doctor.getPassword())) {
      return true;
    } else {
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
  public String loginByEmail(String email, String password) {
    Doctor doctor = doctorMapper.selectDoctorByEmail(email);
    if (doctor == null) {
      return null;
    } else if (Objects.equals(doctor.getStatus(), "disabled")) {
      return "disabled";
    }
    if (encryptionUtil.verifyMD5(password, doctor.getPassword())) {
      return doctor.getDoctorId();
    }
    return null;
  }

  @Override
  public String loginByUsername(String username, String password) {
    Doctor doctor = doctorMapper.selectDoctorByUsername(username);
    if (doctor == null) {
      return null;
    } else if (Objects.equals(doctor.getStatus(), "disabled")) {
      return "disabled";
    }
    if (encryptionUtil.verifyMD5(password, doctor.getPassword())) {
      return doctor.getDoctorId();
    }
    return null;
  }

  @Override
  public String isUsernameExist(String username) {
    String doctorId = doctorMapper.isUsernameExist(username);
    if (doctorId != null) {
      return doctorId;
    }
    return null;
  }

  @Override
  public String isUsernameExist(String doctorId, String username) {
    String selectedDoctorId = doctorMapper.isUsernameExist(username);
    if (!Objects.equals(doctorId, selectedDoctorId)) {
      return doctorId;
    }
    return null;
  }

  @Override
  public String isEmailExist(String email) {
    String doctorId = doctorMapper.isEmailExist(email);
    if (doctorId != null) {
      return doctorId;
    }
    return null;
  }

  @Override
  public String getAvatarBase64(String doctorId) {
    String folder =
        System.getProperty("user.dir")
            + File.separator
            + "uploads"
            + File.separator
            + "doctor_avatars"
            + File.separator;
    String fileName = doctorId + ".jpg";
    Path path = Paths.get(folder + fileName);
    try {
      // 读取文件内容为字节数组
      byte[] imageBytes = Files.readAllBytes(path);

      // 编码为Base64字符串
      String base64Image =
          "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageBytes);

      return base64Image;
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String generateRegisterCode(String email) {
    // 检查Redis中是否已存在该邮箱的验证码
    if (Boolean.TRUE.equals(redisTemplate.hasKey(email))) {
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
  public String generateRegisterCode(String email, char c) {
    // 检查Redis中是否已存在该邮箱的验证码
    if (Boolean.TRUE.equals(redisTemplate.hasKey(email))) {
      // 如果存在，则删除旧的验证码
      redisTemplate.delete(email);
    }

    // 生成一个唯一的注册码
    String registerCode = c + UUID.randomUUID().toString().substring(0, 8);

    // 将新的验证码存储到Redis中，设置过期时间为5分钟
    redisTemplate.opsForValue().set(email, registerCode, 5, TimeUnit.MINUTES);

    return registerCode;
  }

  @Override
  public boolean registerDoctor(Doctor doctor) {
    // 插入医生信息到数据库
    return insert(doctor) != null;
  }

  @Override
  public List<Doctor> selectDoctorByCondition(String queryString, String adminId, int currentPage, int pageSize) {
    logger.info("根据条件查询医生，当前页: {}, 每页记录数: {}, 查询条件: {}", currentPage, pageSize, queryString);
    try {
      // 启动分页
      PageHelper.startPage(currentPage, pageSize);
      logger.info(
          "PageHelper 分页信息: pageNum={}, pageSize={}, startRow={}, endRow={}, total={}, pages={}",
          PageHelper.getLocalPage().getPageNum(),
          PageHelper.getLocalPage().getPageSize(),
          PageHelper.getLocalPage().getStartRow(),
          PageHelper.getLocalPage().getEndRow(),
          PageHelper.getLocalPage().getTotal(),
          PageHelper.getLocalPage().getPages());
      int total = doctorMapper.selectMyDoctorCount(adminId);
      logger.info("获取医生总数成功，总数: {}", total);
      // 判断分页条件是否合理
      // 不能小于0
      if (currentPage < 1) {
        logger.warn("当前页数不能小于1，设置当前页数为1");
        currentPage = 1;
      }
      if (pageSize < 1) {
        logger.warn("每页记录数不能小于1，设置每页记录数为1");
        pageSize = 1;
      }
      // 根据pageSize计算总页数，判断currentPage是否合理
      int totalPages = (int) Math.ceil((double) total / pageSize);
      if (currentPage > totalPages && totalPages != 0) {
        logger.warn("当前页数不能大于总页数，设置当前页数为{}", totalPages);
        currentPage = totalPages;
      }
      List<Doctor> list = doctorMapper.selectDoctorByCondition(queryString, adminId, (currentPage - 1) * pageSize, pageSize);

      PageInfo<Doctor> pageInfo = new PageInfo<>(list);

      logger.info("根据条件查询医生成功，总记录数: {}, 结果: {}", pageInfo.getTotal(), list);
      logger.info(
          "PageHelper 分页信息: pageNum={}, pageSize={}, startRow={}, endRow={}, total={}, pages={}",
          pageInfo.getPageNum(),
          pageInfo.getPageSize(),
          pageInfo.getStartRow(),
          pageInfo.getEndRow(),
          pageInfo.getTotal(),
          pageInfo.getPages());
      return list;
    } catch (Exception e) {
      logger.error("根据条件查询医生失败", e);
      return null;
    }
  }
}

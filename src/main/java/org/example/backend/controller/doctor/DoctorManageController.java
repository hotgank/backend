package org.example.backend.controller.doctor;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.service.others.HospitalService;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.util.JsonParser;
import org.example.backend.util.MailUtils;
import org.example.backend.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor_manage")
public class DoctorManageController {
  private static final Logger logger = LoggerFactory.getLogger(DoctorRegisterController.class);
  @Autowired private MailUtils mailUtils;
  @Autowired private DoctorService doctorService;
  @Autowired private JsonParser jsonParser;
  @Autowired private RedisUtil redisUtil;

  @Qualifier("redisTemplate")
  @Autowired
  private RedisTemplate redisTemplate;

  @PostMapping("/sendOldEmailCode")
  public ResponseEntity<String> sendRegisterCode(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    Doctor doctor = doctorService.selectById(doctorId);
    String email = doctor.getEmail();
    logger.info("收到发送注册码请求，邮箱: {}", email);

    if (email == null || email.isEmpty()) {
      logger.error("邮箱地址为空");
      return ResponseEntity.status(400).body("错误请求");
    }

    String registerCode = doctorService.generateRegisterCode(email);
    logger.info("生成的注册码: {}", registerCode);

    if (mailUtils.sendMail(email, "更改邮箱验证码是: " + registerCode, "更改邮箱")) {
      logger.info("更改码已成功发送到邮箱: {}", email);
      return ResponseEntity.ok("更改码已发送到您的邮箱，请查收");
    } else {
      logger.error("发送邮件失败，邮箱: {}", email);
      return ResponseEntity.status(501).body("发送邮件失败");
    }
  }

  @PostMapping("/sendNewEmailCode")
  public ResponseEntity<String> sendRegisterCode(@RequestBody Map<String, String> body) {
    String email = body.get("newEmail");
    logger.info("收到发送注册码请求，邮箱: {}", email);

    if (email == null || email.isEmpty()) {
      logger.error("邮箱地址为空");
      return ResponseEntity.status(400).body("邮箱地址不能为空");
    }

    String EmailExist = doctorService.isEmailExist(email);
    if (!(EmailExist == null || EmailExist.isEmpty())) {
      return ResponseEntity.status(400).body("邮箱已存在");
    }

    String registerCode = doctorService.generateRegisterCode(email);
    logger.info("生成的注册码: {}", registerCode);

    if (mailUtils.sendMail(email, "更改邮箱验证码是: " + registerCode, "更改邮箱")) {
      logger.info("更改码已成功发送到邮箱: {}", email);
      return ResponseEntity.ok("更改码已发送到您的邮箱，请查收");
    } else {
      logger.error("发送邮件失败，邮箱: {}", email);
      return ResponseEntity.status(501).body("发送邮件失败");
    }
  }

  @PostMapping("/changeEmail")
  public ResponseEntity<String> register(
      @RequestBody Map<String, String> body, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    Doctor doctor = doctorService.selectById(doctorId);
    String oldEmail = doctor.getEmail();
    String newEmail = body.get("newEmail");
    String oldCode = body.get("oldCode");
    String newCode = body.get("newCode");

    logger.info("收到更改邮箱请求，旧邮箱: {}, 新邮箱: {}", oldEmail, newEmail);

    String EmailExist = doctorService.isEmailExist(newEmail);
    if (!(EmailExist == null || EmailExist.isEmpty())) {
      return ResponseEntity.status(400).body("新邮箱已注册过");
    }

    // 检查注册码是否有效
    if (!doctorService.validateRegisterCode(oldEmail, oldCode)) {
      logger.error("旧邮箱更改码无效，邮箱: {}, 注册码: {}", oldEmail, oldCode);
      return ResponseEntity.status(400).body("旧更改码错误或无效");
    }
    if (!doctorService.validateRegisterCode(newEmail, newCode)) {
      logger.error("新邮箱更改码无效，邮箱: {}, 注册码: {}", newEmail, newCode);
      return ResponseEntity.status(400).body("新邮箱码错误或无效");
    }

    doctor.setEmail(newEmail);

    boolean success = doctorService.update(doctor);

    if (success) {
      logger.info("邮箱更改成功，邮箱: {}, 用户名: {}", newEmail, doctor.getUsername());
      redisTemplate.delete(oldEmail);
      redisTemplate.delete(newEmail);
      return ResponseEntity.ok("更改成功");
    } else {
      logger.error("邮箱更改失败，邮箱: {}, 用户名: {}", oldEmail, doctor.getUsername());
      return ResponseEntity.status(500).body("更改失败");
    }
  }

  @PostMapping("/updatePassword")
  public ResponseEntity<String> updatePassword(
      @RequestBody String doctorJson, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String oldPassword = jsonParser.parseJsonString(doctorJson, "oldPassword");
    if (!doctorService.validatePassword(doctorId, oldPassword)) {
      return ResponseEntity.status(400).body("Failed to find doctor information");
    }
    String newPassword = jsonParser.parseJsonString(doctorJson, "newPassword");

    if (newPassword == null) {
      return ResponseEntity.status(400).body("密码不能为空");
    }
    if (doctorService.updatePassword(doctorId, newPassword)) {
      return ResponseEntity.ok("Password updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update password");
    }
  }

  @PostMapping("/sendDeleteCode")
  public ResponseEntity<String> sendDeleteCode(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    Doctor doctor = doctorService.selectById(doctorId);
    String email = doctor.getEmail();
    logger.info("收到注销账号请求，邮箱: {}", email);

    if (email == null || email.isEmpty()) {
      logger.error("邮箱地址为空");
      return ResponseEntity.status(400).body("错误请求");
    }

    String registerCode = doctorService.generateRegisterCode(email);
    logger.info("生成的注销码: {}", registerCode);

    if (mailUtils.sendMail(email, "注销账号验证码是: " + registerCode, "注销账号")) {
      logger.info("更改码已成功发送到邮箱: {}", email);
      return ResponseEntity.ok("注销码已发送到您的邮箱，请查收");
    } else {
      logger.error("发送邮件失败，邮箱: {}", email);
      return ResponseEntity.status(501).body("发送邮件失败");
    }
  }

  @PostMapping("/deleteDoctor")
  public ResponseEntity<String> deleteDoctor(
      @RequestBody String doctorJson, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String password = jsonParser.parseJsonString(doctorJson, "password");
    if (!doctorService.validatePassword(doctorId, password)) {
      return ResponseEntity.status(400).body("Failed to find doctor information");
    }
    String deleteCode = jsonParser.parseJsonString(doctorJson, "deleteCode");
    Doctor doctor = doctorService.selectById(doctorId);
    String email = doctor.getEmail();
    if (!doctorService.validateRegisterCode(email, deleteCode)) {
      logger.error("注销账号码无效，邮箱: {}, 注册码: {}", email, deleteCode);
      return ResponseEntity.status(400).body("注销账号码错误或无效");
    }

    boolean success = doctorService.delete(doctorId);

    if (success) {
      logger.info("注销账号成功，邮箱: {}, 用户名: {}", email, doctor.getUsername());
      redisTemplate.delete(email);
      return ResponseEntity.ok("注销成功");
    } else {
      logger.error("注销账号失败，邮箱: {}, 用户名: {}", email, doctor.getUsername());
      return ResponseEntity.status(500).body("注销失败");
    }
  }
}

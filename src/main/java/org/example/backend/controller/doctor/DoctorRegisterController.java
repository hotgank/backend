package org.example.backend.controller.doctor;

import java.util.Map;
import org.example.backend.entity.doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.backend.Service.doctor.DoctorService;
import org.example.backend.util.MailUtils;


/**
 * 医生注册控制器
 * @author Q
 */
@RestController
@RequestMapping("/api/DoctorRegister")
public class DoctorRegisterController {

  private static final Logger logger = LoggerFactory.getLogger(DoctorRegisterController.class);

  @Autowired
  private MailUtils mailUtils;

  @Autowired
  private DoctorService doctorService;

  @PostMapping("/sendRegisterCode")
  public ResponseEntity<String> sendRegisterCode(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    logger.info("收到发送注册码请求，邮箱: {}", email);

    if (email == null || email.isEmpty()) {
      logger.error("邮箱地址为空");
      return ResponseEntity.status(400).body("邮箱地址不能为空");
    }

    String registerCode = doctorService.generateRegisterCode(email);
    logger.info("生成的注册码: {}", registerCode);

    if (mailUtils.sendMail(email, "您的注册码是: " + registerCode, "注册验证码")) {
      logger.info("注册码已成功发送到邮箱: {}", email);
      return ResponseEntity.ok("注册码已发送到您的邮箱，请查收");
    } else {
      logger.error("发送邮件失败，邮箱: {}", email);
      return ResponseEntity.status(501).body("发送邮件失败");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String registerCode = body.get("registerCode");
    String username = body.get("name");
    String password = body.get("password");
    Doctor doctor = new Doctor();
    doctor.setEmail(email);
    doctor.setName(username);
    doctor.setPassword(password);
    logger.info("收到注册请求，邮箱: {}, 用户名: {}", email, username);

    if (email == null || email.isEmpty() || registerCode == null || registerCode.isEmpty() || username == null || username.isEmpty() || password == null || password.isEmpty()) {
      logger.error("注册信息不完整");
      return ResponseEntity.status(400).body("注册信息不完整");
    }

    // 检查注册码是否有效
    if (!doctorService.validateRegisterCode(email, registerCode)) {
      logger.error("注册码无效，邮箱: {}, 注册码: {}", email, registerCode);
      return ResponseEntity.status(400).body("注册码错误或无效");
    }



//    if (doctorService.isEmailExist(email)) {
//      logger.error("邮箱已存在，邮箱: {}", email);
//      return ResponseEntity.status(400).body("邮箱已存在");
//    }

    boolean success = doctorService.registerDoctor(doctor);

    if (success) {
      logger.info("用户注册成功，邮箱: {}, 用户名: {}", email, username);
      return ResponseEntity.ok("注册成功");
    } else {
      logger.error("用户注册失败，邮箱: {}, 用户名: {}", email, username);
      return ResponseEntity.status(500).body("注册失败");
    }
  }
}
package org.example.backend.controller.doctor;

import java.util.List;
import java.util.Map;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.others.Hospital;
import org.example.backend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.util.MailUtils;
import org.example.backend.service.others.HospitalService;

/**
 * 医生注册控制器
 * @author Q
 */
@RestController
@RequestMapping("/api/DoctorRegister")
public class DoctorRegisterController {

  private static final Logger logger = LoggerFactory.getLogger(DoctorRegisterController.class);

  @Autowired private MailUtils mailUtils;

  @Autowired private DoctorService doctorService;

  @Autowired private HospitalService hospitalService;
  @Autowired private RedisUtil redisUtil;

  @Qualifier("redisTemplate")
  @Autowired
  private RedisTemplate redisTemplate;

  @PostMapping("/sendRegisterCode")
  public ResponseEntity<String> sendRegisterCode(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    logger.info("收到发送注册码请求，邮箱: {}", email);

    if (email == null || email.isEmpty()) {
      logger.error("邮箱地址为空");
      return ResponseEntity.status(400).body("邮箱地址不能为空");
    }

    String EmailExist = doctorService.isEmailExist(email);
    if (!(EmailExist == null || EmailExist.isEmpty())) {
      return ResponseEntity.status(400).body("邮箱已存在");
    }

    String registerCode = doctorService.generateRegisterCode(email,'R');
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
    String name = body.get("name");
    String username = body.get("username");
    String password = body.get("password");
    String workplace = body.get("workplace");
    Doctor doctor = new Doctor();
    doctor.setEmail(email);
    doctor.setName(name);
    doctor.setUsername(username);
    doctor.setPassword(password);
    doctor.setWorkplace(workplace);
    logger.info("收到注册请求，邮箱: {}, 用户名: {}", email, username);

    if (email == null
        || email.isEmpty()
        || registerCode == null
        || registerCode.isEmpty()
        || username == null
        || username.isEmpty()
        || password == null
        || password.isEmpty()
        || workplace == null
        || workplace.isEmpty()
        || name == null
        || name.isEmpty()) {
      logger.error("注册信息不完整");
      return ResponseEntity.status(400).body("注册信息不完整");
    }

    // 检查注册码是否有效
    if (registerCode.charAt(0)!='R'||!doctorService.validateRegisterCode(email, registerCode)) {
      logger.error("注册码无效，邮箱: {}, 注册码: {}", email, registerCode);
      return ResponseEntity.status(400).body("注册码错误或无效");
    }

    String UsernameExist = doctorService.isUsernameExist(username);
    if (!(UsernameExist == null || UsernameExist.isEmpty())) {
      return ResponseEntity.status(400).body("用户名已存在");
    }

    String EmailExist = doctorService.isEmailExist(email);
    if (!(EmailExist == null || EmailExist.isEmpty())) {
      return ResponseEntity.status(400).body("邮箱已存在");
    }

    String success = doctorService.insert(doctor);

    if (success != null) {
      logger.info("用户注册成功，邮箱: {}, 用户名: {}", email, username);
      redisTemplate.delete(email);
      return ResponseEntity.ok("注册成功");
    } else {
      logger.error("用户注册失败，邮箱: {}, 用户名: {}", email, username);
      return ResponseEntity.status(500).body("注册失败");
    }
  }

  @GetMapping("/selectAllHospitals")
  public List<Hospital> selectAllHospitals() {
    return hospitalService.selectAllHospitals();
  }
}

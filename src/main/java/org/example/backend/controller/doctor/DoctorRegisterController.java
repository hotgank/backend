package org.example.backend.controller.doctor;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.example.backend.Service.doctor.DoctorService;
import org.example.backend.util.MailUtils;


/**
 * 医生注册控制器
 * @author Q
 */
@RestController
@RequestMapping("/api/DoctorRegister")
public class DoctorRegisterController {

  @Autowired
  private MailUtils mailUtils;

  @Autowired
  private DoctorService doctorService;

  @PostMapping("/sendRegisterCode")
  public ResponseEntity<String> sendRegisterCode(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String registerCode = doctorService.generateRegisterCode(email);

    if (mailUtils.sendMail(email, "您的注册码是: " + registerCode, "注册验证码")) {
      return ResponseEntity.ok("注册码已发送到您的邮箱，请查收");
    } else {
      return ResponseEntity.status(501).body("发送邮件失败");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(Map<String, String> body) {
//    if (FileUtils.isUsernameExist(request.getName())) {
//      return ResponseEntity.status(400).body("用户名已存在");
//    }
//    if (FileUtils.isEmailExist(request.getEmail())) {
//      return ResponseEntity.status(400).body("邮箱账号已存在");
//    }
//
//    boolean success = doctorService.registerUser(request);
//
//    if (success) {
//      return ResponseEntity.ok("注册成功");
//    } else {
      return ResponseEntity.status(400).body("注册码错误或无效");
//    }
  }
}

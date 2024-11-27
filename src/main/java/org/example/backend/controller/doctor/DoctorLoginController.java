package org.example.backend.controller.doctor;

import java.time.LocalDateTime;
import java.util.Map;
import org.example.backend.entity.doctor.Doctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.backend.util.JwtUtil;
import org.example.backend.util.RedisUtil;
import org.example.backend.util.MailUtils;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.util.JsonParser;

@RestController
@RequestMapping("/api/DoctorLogin")
public class DoctorLoginController {

  private static final Logger log = LoggerFactory.getLogger(DoctorLoginController.class);
  @Autowired
  private DoctorService doctorService;

  @Autowired
  private JwtUtil jwtUtil;  // 假设你有一个 JwtUtil 来处理 JWT 生成

  @Autowired
  private RedisUtil redisUtil;  // 假设你有一个 RedisUtil 来存储 token

  @Autowired
  private JsonParser jsonParser;

  @Autowired
  private MailUtils mailUtils;

  @PostMapping("/loginByEmail")
  public ResponseEntity<String> loginByEmail(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String password = body.get("password");
    log.info("Received login request for email: {}", email);

    // 查询数据库，查找是否有该邮箱的用户
    String doctorId = doctorService.loginByEmail(email, password);
    if (doctorId == null|| doctorId.isEmpty()) {
      return ResponseEntity.badRequest().body("账号或密码错误");
    }else if (doctorId.equals("disabled")){
      return ResponseEntity.badRequest().body("账号已封禁");
    }

    // 生成 JWT token
    String jwtToken = jwtUtil.generateToken(doctorId);

    // 将 token 存储到 Redis（如果需要）
    redisUtil.storeTokenInRedis(doctorId, jwtToken);

    // 获取医生的详细信息
    Doctor doctor = doctorService.selectById(doctorId);
    doctor.setLastLogin(LocalDateTime.now());
    doctor.setStatus("online");
    doctorService.update(doctor);
    String doctorJson = jsonParser.toJsonFromEntity(doctor);
    doctorJson = jsonParser.removeKeyFromJson(doctorJson, "password");
    doctorJson = jsonParser.removeKeyFromJson(doctorJson, "doctorId");
    doctorJson = jsonParser.removeKeyFromJson(doctorJson, "avatarUrl");
    doctorJson = jsonParser.removeKeyFromJson(doctorJson, "status");
    // 构建响应体
    String response = "{\"token\":\"" + jwtToken + "\",\"doctor\":" + doctorJson  + "}";

    // 返回 JWT token 和医生的详细信息
    return ResponseEntity.ok(response);
  }

  @PostMapping("/loginByUsername")
  public ResponseEntity<String> loginByUsername(@RequestBody Map<String, String> body) {
    String username = body.get("username");
    String password = body.get("password");
    log.info("Received login request for username: {}", username);

    // 查询数据库，查找是否有该用户名的用户
    String doctorId = doctorService.loginByUsername(username, password);
    if (doctorId == null|| doctorId.isEmpty()) {
      return ResponseEntity.badRequest().body("账号或密码错误");
    }else if (doctorId.equals("disabled")){
      return ResponseEntity.badRequest().body("账号已封禁");
    }

    // 生成 JWT token
    String jwtToken = jwtUtil.generateToken(doctorId);

    // 将 token 存储到 Redis（如果需要）
    redisUtil.storeTokenInRedis(doctorId, jwtToken);

    // 获取医生的详细信息
    Doctor doctor = doctorService.selectById(doctorId);
    doctor.setLastLogin(LocalDateTime.now());
    doctor.setStatus("online");
    doctorService.update(doctor);
    String doctorJson = jsonParser.toJsonFromEntity(doctor);
    doctorJson = jsonParser.removeKeyFromJson(doctorJson, "password");
    doctorJson = jsonParser.removeKeyFromJson(doctorJson, "doctorId");
    doctorJson = jsonParser.removeKeyFromJson(doctorJson, "avatarUrl");
    doctorJson = jsonParser.removeKeyFromJson(doctorJson, "status");
    // 构建响应体
    String response = "{\"token\":\"" + jwtToken + "\",\"doctor\":" + doctorJson  + "}";

    // 返回 JWT token 和医生的详细信息
    return ResponseEntity.ok(response);
  }

  @PostMapping("/sendChangePasswdCode")
  public ResponseEntity<String> sendRegisterCode(@RequestBody String doctorJson) {
    String email = jsonParser.parseJsonString(doctorJson, "email");
    if (email == null || email.isEmpty()) {
      log.error("邮箱地址为空");
      return ResponseEntity.status(400).body("错误请求");
    }
    String  username= jsonParser.parseJsonString(doctorJson, "username");

    if (username == null || username.isEmpty()) {
      log.error("用户名为空");
      return ResponseEntity.status(400).body("错误请求");
    }
    Doctor doctor = doctorService.selectDoctorByEmail(email);
    if (doctor==null){
      log.error("该邮箱未注册");
      return ResponseEntity.status(400).body("该邮箱未注册");
    }
    if (!doctor.getUsername().equals(username)){
      log.error("用户名错误");
      return ResponseEntity.status(400).body("用户名错误");
    }

    log.info("收到发送注册码请求，邮箱: {}", email);

    String registerCode = doctorService.generateRegisterCode(email);
    log.info("生成的忘记密码验证码: {}", registerCode);

    if (mailUtils.sendMail(email, "忘记密码验证码是: " + registerCode, "忘记密码")) {
      log.info("忘记密码验证码已成功发送到邮箱: {}", email);
      return ResponseEntity.ok("忘记密码验证码已发送到您的邮箱，请查收");
    } else {
      log.error("发送邮件失败，邮箱: {}", email);
      return ResponseEntity.status(501).body("发送邮件失败");
    }
  }

  @PostMapping("/ChangePasswd")
  public ResponseEntity<String> changePassword(@RequestBody String doctorJson) {
    String email = jsonParser.parseJsonString(doctorJson, "email");
    String  changeCode = jsonParser.parseJsonString(doctorJson, "changeCode");
    String newPassword = jsonParser.parseJsonString(doctorJson, "newPassword");
    Doctor doctor = doctorService.selectDoctorByEmail(email);
    if (doctor==null){
      log.error("该邮箱未注册");
      return ResponseEntity.status(400).body("该邮箱未注册");
    }
    if (!doctorService.validateRegisterCode(email, changeCode)) {
      log.error("忘记密码码无效，邮箱: {}, 注册码: {}", email, changeCode);
      return ResponseEntity.status(400).body("忘记密码码错误或无效");
    }
    if (newPassword == null){
      return ResponseEntity.status(400).body("密码不能为空");
    }
    if (doctorService.updatePassword(doctor.getDoctorId(), newPassword)) {
      return ResponseEntity.ok("Password changed successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update password");
    }
  }
}
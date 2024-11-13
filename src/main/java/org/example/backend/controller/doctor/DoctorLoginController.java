package org.example.backend.controller.doctor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.user.User;
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
import org.example.backend.service.doctor.DoctorService;

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

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String password = body.get("password");
    log.info("Received login request for email: {}", email);
    // 查询数据库，查找是否有该邮箱的用户
    String doctorId = doctorService.loginByEmail(email, password);
    if (doctorId == null) {
      return ResponseEntity.badRequest().body("账号或密码错误");
    }

    // 生成 JWT token
    String jwtToken = jwtUtil.generateToken(doctorId);

    // 将 token 存储到 Redis（如果需要）
    redisUtil.storeTokenInRedis(doctorId, jwtToken);

    // 返回 JWT token
    return ResponseEntity.ok("{\"token\":\"" + jwtToken + "\"}");
  }
}
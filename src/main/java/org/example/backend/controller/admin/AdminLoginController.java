package org.example.backend.controller.admin;

import java.time.LocalDateTime;
import java.util.Map;
import org.example.backend.entity.admin.Admin;
import org.example.backend.service.admin.AdminService;
import org.example.backend.util.JsonParser;
import org.example.backend.util.JwtUtil;
import org.example.backend.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/AdminLogin")
public class AdminLoginController {

  private static final Logger log = LoggerFactory.getLogger(AdminLoginController.class);
  @Autowired private AdminService adminService;

  @Autowired private JwtUtil jwtUtil; // 假设你有一个 JwtUtil 来处理 JWT 生成

  @Autowired private RedisUtil redisUtil; // 假设你有一个 RedisUtil 来存储 token

  @Autowired private JsonParser jsonParser;

  @PostMapping("/loginByEmail")
  public ResponseEntity<String> loginByEmail(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String password = body.get("password");
    log.info("Received login request for email: {}", email);

    // 查询数据库，查找是否有该邮箱的用户
    String adminId = adminService.verifyByEmailAndPassword(email, password);
    if (adminId == null) {
      return ResponseEntity.badRequest().body("邮箱或密码错误");
    } else if (adminId.equals("disabled")) {
      return ResponseEntity.badRequest().body("账号已封禁");
    }

    // 生成 JWT token
    String jwtToken = jwtUtil.generateToken(adminId);

    // 将 token 存储到 Redis（如果需要）
    redisUtil.storeTokenInRedis(adminId, jwtToken);

    // 获取医生的详细信息
    Admin admin = adminService.selectById(adminId);
    admin.setLastLogin(LocalDateTime.now());
    admin.setStatus("active");
    adminService.update(admin);
    admin.setAdminId(null);
    admin.setPassword(null);
    // 构建响应体
    String response =
        "{\"token\":\"" + jwtToken + "\",\"admin\":" + jsonParser.toJsonFromEntity(admin) + "}";

    // 返回 JWT token 和医生的详细信息
    return ResponseEntity.ok(response);
  }

  @PostMapping("/loginByUsername")
  public ResponseEntity<String> loginByUsername(@RequestBody Map<String, String> body) {
    String username = body.get("username");
    String password = body.get("password");
    log.info("Received login request for username: {}", username);

    // 查询数据库，查找是否有该邮箱的用户
    String adminId = adminService.verifyByUsernameAndPassword(username, password);
    if (adminId == null) {
      return ResponseEntity.badRequest().body("账号或密码错误");
    } else if (adminId.equals("disabled")) {
      return ResponseEntity.badRequest().body("账号已封禁");
    }

    // 生成 JWT token
    String jwtToken = jwtUtil.generateToken(adminId);

    // 将 token 存储到 Redis（如果需要）
    redisUtil.storeTokenInRedis(adminId, jwtToken);

    // 获取医生的详细信息
    Admin admin = adminService.selectById(adminId);
    admin.setLastLogin(LocalDateTime.now());
    admin.setStatus("active");
    adminService.update(admin);
    admin.setAdminId(null);
    admin.setPassword(null);
    // 构建响应体
    String response =
        "{\"token\":\"" + jwtToken + "\",\"admin\":" + jsonParser.toJsonFromEntity(admin) + "}";

    // 返回 JWT token 和医生的详细信息
    return ResponseEntity.ok(response);
  }
}

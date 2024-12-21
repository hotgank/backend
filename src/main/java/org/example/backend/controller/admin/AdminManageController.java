package org.example.backend.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.entity.admin.Admin;
import org.example.backend.service.admin.AdminManageService;
import org.example.backend.service.admin.AdminService;
import org.example.backend.util.MailUtils;
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

import java.util.Map;

@RestController
@RequestMapping("/api/admin_manage")
public class AdminManageController {

    private static final Logger logger = LoggerFactory.getLogger(AdminManageController.class);

    @Autowired private MailUtils mailUtils;

    @Autowired private AdminService adminService;

    @Autowired private AdminManageService adminManageService;

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendOldEmailCode")
    public ResponseEntity<String> sendRegisterCode(HttpServletRequest request) {
        String adminId = (String) request.getAttribute("userId");
        Admin admin = adminService.selectById(adminId);
        String email = admin.getEmail();
        logger.info("收到发送注册码请求，邮箱: {}", email);

        if (email == null || email.isEmpty()) {
            logger.error("邮箱地址为空");
            return ResponseEntity.status(400).body("错误请求");
        }

        String registerCode = adminManageService.generateRegisterCode(email);
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

        String EmailExist = adminManageService.isEmailExist(email);
        if (!(EmailExist == null || EmailExist.isEmpty())) {
            return ResponseEntity.status(400).body("邮箱已存在");
        }

        String registerCode = adminManageService.generateRegisterCode(email);
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
        String adminId = (String) request.getAttribute("userId");
        Admin admin = adminService.selectById(adminId);
        String oldEmail = admin.getEmail();
        String newEmail = body.get("newEmail");
        String oldCode = body.get("oldCode");
        String newCode = body.get("newCode");

        logger.info("收到更改邮箱请求，旧邮箱: {}, 新邮箱: {}", oldEmail, newEmail);

        String EmailExist = adminManageService.isEmailExist(newEmail);
        if (!(EmailExist == null || EmailExist.isEmpty())) {
            return ResponseEntity.status(400).body("新邮箱已注册过");
        }

        // 检查注册码是否有效
        if (!adminManageService.validateRegisterCode(oldEmail, oldCode)) {
            logger.error("旧邮箱更改码无效，邮箱: {}, 注册码: {}", oldEmail, oldCode);
            return ResponseEntity.status(400).body("旧更改码错误或无效");
        }
        if (!adminManageService.validateRegisterCode(newEmail, newCode)) {
            logger.error("新邮箱更改码无效，邮箱: {}, 注册码: {}", newEmail, newCode);
            return ResponseEntity.status(400).body("新邮箱码错误或无效");
        }

        admin.setEmail(newEmail);

        boolean success = adminService.update(admin);

        if (success) {
            logger.info("邮箱更改成功，邮箱: {}, 用户名: {}", newEmail, admin.getUsername());
            redisTemplate.delete(oldEmail);
            redisTemplate.delete(newEmail);
            return ResponseEntity.ok("更改成功");
        } else {
            logger.error("邮箱更改失败，邮箱: {}, 用户名: {}", oldEmail, admin.getUsername());
            return ResponseEntity.status(500).body("更改失败");
        }
    }
}

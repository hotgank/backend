package org.example.backend.controller.admin;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import org.example.backend.entity.admin.Admin;
import org.example.backend.service.admin.AdminService;
import org.example.backend.util.JsonParser;
import org.example.backend.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private static final Logger log = LoggerFactory.getLogger(AdminController.class);

  @Autowired private AdminService adminService;

  @Autowired private JsonParser jsonParser;

  @Autowired private RedisUtil redisUtil;

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAll() {
    // 调用服务层来查询所有管理员信息
    List<Admin> admins = adminService.selectAll();
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(admins));
  }

  @GetMapping("/selectSecondAdmins")
  public ResponseEntity<String> selectSecondAdmins() {
    // 调用服务层来查询所有管理员信息
    List<Admin> admins = adminService.selectSecondAdmins();
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(admins));
  }

  @PostMapping("/selectById")
  public ResponseEntity<String> selectById(@RequestBody String adminIdJson) {
    String adminId = jsonParser.parseJsonString(adminIdJson, "adminId");
    // 调用服务层来根据adminId查询管理员信息
    Admin selectedAdmin = adminService.selectById(adminId);
    // 调用服务层来查询指定管理员信息
    if (selectedAdmin != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntity(selectedAdmin));
    } else {
      return ResponseEntity.status(500).body("Failed to find admin information");
    }
  }

  @GetMapping("/selectMyInfo")
  public ResponseEntity<String> selectMyInfo(HttpServletRequest request) {
    String adminId = (String) request.getAttribute("userId");
    // 调用服务层来根据adminId查询管理员信息
    Admin selectedAdmin = adminService.selectById(adminId);
    // 调用服务层来查询指定管理员信息
    if (selectedAdmin != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntity(selectedAdmin));
    } else {
      return ResponseEntity.status(500).body("Failed to add find information");
    }
  }

  @PostMapping("/create")
  public ResponseEntity<String> createAdmin(@RequestBody Admin admin) {
    // 调用服务层来添加管理员信息到数据库
    String result = adminService.insert(admin);

    if (result != null) {
      return ResponseEntity.ok("Admin information added successfully, adminId: " + result);
    } else {
      return ResponseEntity.status(500).body("Failed to add admin information");
    }
  }

  @PostMapping("/updateMyEmailAndPhone")
  public ResponseEntity<String> updateMyEmailAndPhone(
      HttpServletRequest request, @RequestBody String AdminJson) {
    String adminId = (String) request.getAttribute("userId");
    String email = jsonParser.parseJsonString(AdminJson, "email");
    String phone = jsonParser.parseJsonString(AdminJson, "phone");
    // 调用服务层来更新管理员信息
    boolean success = adminService.updateMyEmailAndPhone(adminId, email, phone);

    if (success) {
      return ResponseEntity.ok("Admin information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update admin information");
    }
  }

  @PostMapping("/updateMyPassword")
  public ResponseEntity<String> updateMyPassword(
      HttpServletRequest request, @RequestBody String AdminJson) {
    String adminId = (String) request.getAttribute("userId");
    String currentPassword = jsonParser.parseJsonString(AdminJson, "currentPassword");
    String newPassword = jsonParser.parseJsonString(AdminJson, "newPassword");
    String confirmPassword = jsonParser.parseJsonString(AdminJson, "confirmPassword");

    if (!adminService.verifyByIdAndPassword(adminId, currentPassword)) {
      return ResponseEntity.status(400).body("Password not true");
    }
    if (Objects.equals(newPassword, confirmPassword)) {
      // 调用服务层来更新管理员信息
      boolean success = adminService.updateMyPassword(adminId, newPassword);

      if (success) {
        return ResponseEntity.ok("Admin information updated successfully");
      } else {
        return ResponseEntity.status(500).body("Failed to update admin information");
      }
    } else {
      return ResponseEntity.status(400).body("Password not equals");
    }
  }

  @PostMapping("/edit")
  public ResponseEntity<String> editAdmin(@RequestBody Admin admin) {
    // 调用服务层来更新管理员信息
    boolean success = adminService.update(admin);

    if (success) {
      return ResponseEntity.ok("Admin information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update admin information");
    }
  }

  @PostMapping("/activate")
  public ResponseEntity<String> activateAdmin(@RequestBody String adminIdJson) {
    String adminId = jsonParser.parseJsonString(adminIdJson, "adminId");
    // 调用服务层来删除管理员信息
    boolean success = adminService.activateAdmin(adminId);

    if (success) {
      return ResponseEntity.ok("Admin information activated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to activate admin information");
    }
  }

  @PostMapping("/ban")
  public ResponseEntity<String> banAdmin(@RequestBody String adminIdJson, HttpServletRequest request) {
    String myAdminId = (String) request.getAttribute("userId");
    String adminId = jsonParser.parseJsonString(adminIdJson, "adminId");
    // 调用服务层来禁用医生账户
    String token=redisUtil.getTokenFromRedis(adminId);
    if (token!=null) {
      boolean success =redisUtil.deleteTokenFromRedis(adminId);
      if (success) {
        boolean success1 = adminService.banAdmin(adminId);
        if (success1) {
          return ResponseEntity.ok("Admin information banned successfully");
        }
      }
    } else {
      boolean success = adminService.banAdmin(adminId);
      if (success) {
        return ResponseEntity.ok("Admin information banned successfully");
      }
    }
    return ResponseEntity.status(500).body("Failed to ban admin information");
    if(Objects.equals(myAdminId, adminId)){
      return ResponseEntity.badRequest().body("不能禁用自己");
    }
    // 调用服务层来删除管理员信息
    boolean success = adminService.banAdmin(adminId);

    if (success) {
      return ResponseEntity.ok("Admin banned successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to ban admin");
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<String> deleteAdmin(@RequestBody String adminIdJson, HttpServletRequest request) {
    String myAdminId = (String) request.getAttribute("userId");
    String adminId = jsonParser.parseJsonString(adminIdJson, "adminId");
    if(Objects.equals(myAdminId, adminId)){
      return ResponseEntity.badRequest().body("不能删除自己");
    }
    // 调用服务层来删除管理员信息
    boolean success = adminService.delete(adminId);

    if (success) {
      return ResponseEntity.ok("Admin information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to delete admin information");
    }
  }

  @GetMapping("/information")
  public ResponseEntity<String> getInformation(HttpServletRequest request) {
    try {
      // 从请求中获取用户ID
      String userId = (String) request.getAttribute("userId");
      Admin admin = adminService.selectById(userId);
      String result =
          jsonParser.removeKeyFromJson(
              jsonParser.removeKeyFromJson(jsonParser.toJsonFromEntity(admin), "adminId"),
              "password");
      result = jsonParser.removeKeyFromJson(result, "avatarUrl");
      result = jsonParser.removeKeyFromJson(result, "status");

      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed");
    }
  }

  @PostMapping("/upload_avatar_base64")
  public ResponseEntity<String> uploadAvatarBase64(
      @RequestBody String base64Image, HttpServletRequest request) {
    String adminId = (String) request.getAttribute("userId");

    // 解析 JSON 字符串
    base64Image = jsonParser.parseJsonString(base64Image, "base64Image");

    // 检查 Base64 数据的格式
    if (base64Image == null || !base64Image.startsWith("data:image")) {
      return new ResponseEntity<>("Invalid image format", HttpStatus.BAD_REQUEST);
    }

    // 提取 Base64 字符串中的数据部分
    String[] base64Parts = base64Image.split(",", 2);
    if (base64Parts.length != 2) {
      return new ResponseEntity<>("Invalid Base64 data", HttpStatus.BAD_REQUEST);
    }

    String base64Data = base64Parts[1];

    // 额外的检查：确保 Base64 数据的长度是 4 的倍数
    if (base64Data.length() % 4 != 0) {
      return new ResponseEntity<>(
          "Base64 data length is not a multiple of 4", HttpStatus.BAD_REQUEST);
    }

    try {
      // 解码 Base64 字符串为字节数组
      byte[] imageBytes = Base64.getDecoder().decode(base64Data);

      // 构建文件保存路径
      String folder =
          System.getProperty("user.dir")
              + File.separator
              + "uploads"
              + File.separator
              + "admin_avatars"
              + File.separator;
      String fileName = adminId + ".jpg";

      // 确保文件夹存在
      File directory = new File(folder);
      if (!directory.exists()) {
        directory.mkdirs();
      }

      // 保存文件
      Path path = Paths.get(folder + fileName);
      Files.write(path, imageBytes);

      // 返回图片的 URL
      String imageUrl = "https://zeropw.cn:8081/admin_avatars/" + fileName;
      Admin admin = adminService.selectById(adminId);
      admin.setAvatarUrl(imageUrl);

      if (!adminService.update(admin)) {
        return ResponseEntity.status(500).body("Failed to upload avatar");
      }

      return ResponseEntity.ok("Successfully uploaded");
    } catch (IllegalArgumentException | IOException e) {
      log.error("Error occurred while uploading Base64 image", e);
      return new ResponseEntity<>(
          "Error occurred while uploading image", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get_avatar_base64")
  public ResponseEntity<String> getAvatarBase64(HttpServletRequest request) {
    String adminId = (String) request.getAttribute("userId");
    String folder =
        System.getProperty("user.dir")
            + File.separator
            + "uploads"
            + File.separator
            + "admin_avatars"
            + File.separator;
    String fileName = adminId + ".jpg";
    Path path = Paths.get(folder + fileName);
    log.info(folder + fileName);
    try {
      // 读取文件内容为字节数组
      byte[] imageBytes = Files.readAllBytes(path);

      // 编码为Base64字符串
      String base64Image =
          "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageBytes);

      return ResponseEntity.ok("{\"base64Image\": \"" + base64Image + "\"}");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}

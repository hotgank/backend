package org.example.backend.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import org.example.backend.dto.UserGetDoctorDTO;
import org.example.backend.entity.user.User;
import org.example.backend.service.user.UserService;
import org.example.backend.util.ExcelReader;
import org.example.backend.util.JsonParser;
import org.example.backend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息相关接口
 * @author Q
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired private UserService userService;

  @Autowired private JsonParser jsonParser;

  @Autowired private ExcelReader excelReader;

  @Autowired private RedisUtil redisUtil;

  @PostMapping("/uploadUsername")
  public ResponseEntity<String> uploadUsername(
      @RequestBody Map<String, String> payload, HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");

    // 从 JSON 请求体中获取 username 字段
    String username = payload.get("username");
    if (username == null || username.isEmpty()) {
      return ResponseEntity.badRequest().body("Username is required");
    }

    // 更新用户名
    userService.updateUsername(userId, username);

    return ResponseEntity.ok("Successfully uploaded username");
  }

  // 获取用户个人信息
  @GetMapping("/getUserInfo")
  public ResponseEntity<String> getUserInfo(HttpServletRequest request) {
    // 从请求中获取用户ID
    String userId = (String) request.getAttribute("userId");

    // 调用服务层来根据userId查询用户信息
    User selectedUser = userService.selectById(userId);
    if (selectedUser != null) {
      return ResponseEntity.ok(
          "{\"username\":\""
              + selectedUser.getUsername()
              + "\",\"phone\":\""
              + selectedUser.getPhone()
              + "\",\"avatarUrl\":\""
              + selectedUser.getAvatarUrl()
              + "\",\"openid\":\""
              + selectedUser.getOpenid()
              + "\"}");
    } else {
      return ResponseEntity.status(500).body("Failed to Get user information");
    }
  }

  @GetMapping("/selectUserCount")
  public ResponseEntity<String> selectUserCount(HttpServletRequest request) {
    String adminId = (String) request.getAttribute("userId");
    if (adminId != null && !adminId.isEmpty() && adminId.charAt(0) == 'A') {
      int userCount = userService.selectUserCount();
      return ResponseEntity.ok("{\"userCount\":\"" + userCount + "\"}");
    } else {
      return ResponseEntity.status(500).body("Failed to Get user information");
    }
  }

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAll() {

    // 调用服务层来查询所有用户信息
    List<User> allUsers = userService.selectAll();

    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(allUsers));
  }

  @GetMapping("/selectById")
  public ResponseEntity<String> selectById(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    // 调用服务层来根据userId查询用户信息
    User selectedUser = userService.selectById(userId);

    if (selectedUser != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntity(selectedUser));
    } else {
      return ResponseEntity.status(500).body("Failed to add user information");
    }
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateUser(@RequestBody User user, HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    user.setUserId(userId);
    boolean success = userService.update(user);

    if (success) {
      return ResponseEntity.ok("User information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update user information");
    }
  }

  @PostMapping("/add")
  public ResponseEntity<String> addUser(@RequestBody User user) {

    // 调用服务层来添加用户信息到数据库
    String result = userService.insert(user);

    if (result != null) {
      return ResponseEntity.ok("User information added successfully, userId: " + result);
    } else {
      return ResponseEntity.status(500).body("Failed to add user information");
    }
  }

  @PostMapping("/ban")
  public ResponseEntity<String> banAccount(@RequestBody String userJson) {
    String userId = jsonParser.parseJsonString(userJson, "userId");

    // 调用服务层来禁用用户账户
    String token=redisUtil.getTokenFromRedis(userId);
    if (token!=null) {
      boolean success =redisUtil.deleteTokenFromRedis(userId);
      if (success) {
        boolean success1 = userService.banAccount(userId);
        if (success1) {
          return ResponseEntity.ok("User account banned successfully");
        }
      }
    } else {
      boolean success = userService.banAccount(userId);
      if (success) {
        return ResponseEntity.ok("User account banned successfully");
      }
    }
    return ResponseEntity.status(500).body("Failed to ban user account");
  }

  @PostMapping("/active")
  public ResponseEntity<String> activeAccount(@RequestBody String userJson) {
    String userId = jsonParser.parseJsonString(userJson, "userId");

    // 调用服务层来激活用户账户
    boolean success = userService.activeAccount(userId);

    if (success) {
      return ResponseEntity.ok("User account activated successfully");
    }
    return ResponseEntity.status(500).body("Failed to active user account");
  }

  @PostMapping("/adminEdit")
  public ResponseEntity<String> editUsername(@RequestBody String userJson) {
    String userId = jsonParser.parseJsonString(userJson, "userId");
    String username = jsonParser.parseJsonString(userJson, "username");
    // 调用服务层来修改用户名
    boolean success = userService.editUsername(userId, username);
    if (success) {
      return ResponseEntity.ok("User username updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update username");
    }
  }

  @PostMapping("/insertAll")
  public ResponseEntity<String> insertAll(@RequestBody String urlJson) {
    String url = jsonParser.parseJsonString(urlJson, "url");
    List<User> users = excelReader.readExcel(url, User.class);
    // 调用服务层来批量添加用户信息到数据库
    boolean success = userService.insertAllUser(users);
    if (success) {
      return ResponseEntity.ok("User information added successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to add user information");
    }
  }

  @PostMapping("/deleteById")
  public ResponseEntity<String> deleteById(@RequestBody String userIdJson) {
    String userId = jsonParser.parseJsonString(userIdJson, "userId");
    // 调用服务层来删除用户信息
    boolean success = userService.delete(userId);
    if (success) {
      return ResponseEntity.ok("User information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to add user information");
    }
  }

  // 获取所有已认证的医生
  @PostMapping("/selectAllQualifiedDoctors")
  public ResponseEntity<String> selectAllQualifiedDoctors(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    List<UserGetDoctorDTO> allQualifiedDoctors = userService.selectAllQualifiedDoctors(userId);
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(allQualifiedDoctors));
  }

  @PostMapping("/selectPage")
  public ResponseEntity<List<User>> selectPage(@RequestBody String jsonString) {
    int currentPage = jsonParser.parseJsonInt(jsonString, "currentPage");
    int pageSize = jsonParser.parseJsonInt(jsonString, "pageSize");
    String queryString = jsonParser.parseJsonString(jsonString, "queryString");
    List<User> users = userService.selectUserByCondition(queryString, currentPage, pageSize);
    if (users == null){
      return ResponseEntity.status(500).body(null);
    }
    users.forEach(user -> user.setPassword(""));
    return ResponseEntity.ok(users);
  }

  @GetMapping("/selectUserStatus")
  public ResponseEntity<String> selectUserStatus(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    User user = userService.selectById(userId);
    if (user == null) {
      return ResponseEntity.status(500).body("Failed to get user status");
    }
    return ResponseEntity.ok("{\"status\":\"" + user.getStatus() + "\"}");
  }
}

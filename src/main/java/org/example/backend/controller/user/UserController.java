package org.example.backend.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.example.backend.entity.user.User;
import org.example.backend.service.user.UserService;
import org.example.backend.util.ExcelReader;
import org.example.backend.util.JsonParser;
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
  @Autowired
  private UserService userService;

  @Autowired
  private JsonParser jsonParser;

  @Autowired
  private ExcelReader excelReader;

  @GetMapping("/selectById")
  public ResponseEntity<String> selectById(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    // 调用服务层来根据userId查询用户信息
    User selectedUser = userService.selectById(userId);

    if (selectedUser != null) {
      return ResponseEntity.ok(selectedUser.toString());
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

  @PostMapping("insertAll")
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
  public ResponseEntity<String> deleteById(@RequestBody String userIdJson ) {
    String userId = jsonParser.parseJsonString(userIdJson, "userId");
    // 调用服务层来删除用户信息
    boolean success = userService.delete(userId);
    if (success) {
      return ResponseEntity.ok("User information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to add user information");
    }
  }
}

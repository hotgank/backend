package org.example.backend.controller.user;

import org.example.backend.entity.user.User;
import org.example.backend.service.user.UserService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAll() {

    // 调用服务层来查询所有孩子信息
    String result = userService.selectAll().toString();

    return ResponseEntity.ok(result);
  }

  @PostMapping("/selectById")
  public ResponseEntity<String> selectById(@RequestBody String userIdJson) {
    String userId = JsonParser.parseJsonString(userIdJson, "userId");
    // 调用服务层来根据userId查询用户信息
    User selectedUser = userService.selectById(userId);
    // 调用服务层来查询指定孩子信息
    if (selectedUser != null) {
      return ResponseEntity.ok(selectedUser.toString());
    } else {
      return ResponseEntity.status(500).body("Failed to add user information");
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

  @PostMapping("/update")
  public ResponseEntity<String> updateUser(@RequestBody User user) {

    // 调用服务层来更新孩子信息
    boolean success = userService.update(user);

    if (success) {
      return ResponseEntity.ok("User information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update user information");
    }
  }

  @PostMapping("/deleteById")
  public ResponseEntity<String> deleteById(@RequestBody String userIdJson) {
    String userId = JsonParser.parseJsonString(userIdJson, "userId");
    // 调用服务层来删除用户信息
    boolean success = userService.delete(userId);
    if (success) {
      return ResponseEntity.ok("User information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to add user information");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody User user) {
    // 调用服务层来注册用户信息
    String result = userService.register(user);
    if (result != null) {
      return ResponseEntity.ok("Registered successfully, userId: " + result);
    } else {
      return ResponseEntity.status(500).body("Failed to register user information");
    }
  }
}

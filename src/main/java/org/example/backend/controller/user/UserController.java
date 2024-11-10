package org.example.backend.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.entity.user.User;
import org.example.backend.service.serviceImpl.user.ParentChildRelationImpl;
import org.example.backend.service.user.UserService;
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


  // 获取用户个人信息
  @GetMapping("/GetUserInfo")
  public ResponseEntity<String> getUserInfo(HttpServletRequest request) {
    // 从请求中获取用户ID
    String userId = (String) request.getAttribute("userId");
    // 调用服务层来根据userId查询用户信息
    User selectedUser = userService.selectById(userId);
    if (selectedUser != null){
      return ResponseEntity.ok("{\"username\":\""+selectedUser.getUsername()
          +"\",\"phone\":\""+selectedUser.getPhone()
          +"\",\"avatarUrl\":\""+selectedUser.getAvatarUrl()
          +"\",\"openid\":\""+selectedUser.getOpenid()+"\"}");
    } else {
      return ResponseEntity.status(500).body("Failed to Get user information");
    }
  }

  //测试用
  @PostMapping("/selectById")
  public ResponseEntity<String> selectById( @RequestBody String userIdJson,HttpServletRequest request) {
    //String userId = (String) request.getAttribute("userId");
    String userId = JsonParser.parseJsonString(userIdJson, "userId");
    // 调用服务层来根据userId查询用户信息
    User selectedUser = userService.selectById(userId);

    if (selectedUser != null) {
      //将user的字段返回
      return ResponseEntity.ok("{\"username\":\""+selectedUser.getUsername()
          +"\",\"phone\":\""+selectedUser.getPhone()
          +"\",\"avatarUrl\":\""+selectedUser.getAvatarUrl()
          +"\",\"openid\":\""+selectedUser.getOpenid()+"\"}");
    } else {
      return ResponseEntity.status(500).body("Failed to Get user information");
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

  @PostMapping("/deleteById")
  public ResponseEntity<String> deleteById(@RequestBody String userIdJson ) {
    String userId = JsonParser.parseJsonString(userIdJson, "userId");
    // 调用服务层来删除用户信息
    boolean success = userService.delete(userId);
    if (success) {
      return ResponseEntity.ok("User information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to add user information");
    }
  }
}

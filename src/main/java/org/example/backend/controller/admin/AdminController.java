package org.example.backend.controller.admin;

import org.example.backend.entity.admin.Admin;
import org.example.backend.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
  @Autowired
  private AdminService adminService;
  @RequestMapping("/add")
  public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
    // 调用服务层来添加管理员信息到数据库
    boolean success = adminService.createAdmin(admin);

    if (success) {
      return ResponseEntity.ok("Child information added successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to add child information");
    }
  }
}

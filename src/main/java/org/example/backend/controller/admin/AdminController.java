package org.example.backend.controller.admin;

import org.example.backend.entity.admin.Admin;
import org.example.backend.service.admin.AdminService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
  @Autowired
  private AdminService adminService;

  @Autowired
  private JsonParser jsonParser;

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAll() {
    // 调用服务层来查询所有管理员信息
    String result = adminService.selectAll().toString();

    return ResponseEntity.ok(result);
  }

  @RequestMapping("/selectById")
  public ResponseEntity<String> selectById(@RequestBody String adminIdJson) {
    String adminId = jsonParser.parseJsonString(adminIdJson, "adminId");
    // 调用服务层来根据adminId查询管理员信息
    Admin selectedAdmin = adminService.selectById(adminId);
    // 调用服务层来查询指定管理员信息
    if (selectedAdmin != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntity(selectedAdmin));
    } else {
      return ResponseEntity.status(500).body("Failed to add admin information");
    }
  }

  @RequestMapping("/add")
  public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
    // 调用服务层来添加管理员信息到数据库
    String result = adminService.insert(admin);

    if (result != null) {
      return ResponseEntity.ok("Admin information added successfully, adminId: " + result);
    } else {
      return ResponseEntity.status(500).body("Failed to add admin information");
    }
  }

  @RequestMapping("/update")
  public ResponseEntity<String> updateAdmin(@RequestBody Admin admin) {
    // 调用服务层来更新管理员信息
    boolean success = adminService.update(admin);

    if (success) {
      return ResponseEntity.ok("Admin information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update admin information");
    }
  }

  @RequestMapping("/delete")
  public ResponseEntity<String> deleteAdmin(@RequestBody String adminIdJson) {
    String adminId = jsonParser.parseJsonString(adminIdJson, "adminId");
    // 调用服务层来删除管理员信息
    boolean success = adminService.delete(adminId);

    if (success) {
      return ResponseEntity.ok("Admin information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to delete admin information");
    }
  }
}

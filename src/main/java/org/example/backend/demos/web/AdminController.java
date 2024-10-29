package org.example.backend.demos.web;

import org.example.backend.Service.Admin.AdminService;
import org.example.backend.Entity.Admin.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author q
 * 测试
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private AdminService adminService;

  @PostMapping("/all")
  public List<Admin> getAllAdmins() {
    return adminService.getAll();
  }

  @PostMapping("/create")
  public boolean createAdmin(Admin admin) {
    return adminService.createAdmin(admin);
  }
  @PostMapping("/delete")
  public boolean deleteAdmin(String adminId) {
    return adminService.deleteAdmin(adminId);
  }
}

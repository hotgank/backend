package org.example.backend.service.admin;

import java.util.List;
import org.example.backend.entity.admin.Admin;

public interface AdminService {
  Admin getById(String adminId);
  List<Admin> getAll();
  boolean createAdmin(Admin admin);
  boolean updateAdmin(Admin admin);
  boolean deleteAdmin(String adminId);
}

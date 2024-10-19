package org.example.backend.Service.Admin;

import java.util.List;
import org.example.backend.Entity.Admin.Admin;

public interface AdminService {
  Admin getById(String adminId);
  List<Admin> getAll();
  boolean createAdmin(Admin admin);
  boolean updateAdmin(Admin admin);
  boolean deleteAdmin(String adminId);
}

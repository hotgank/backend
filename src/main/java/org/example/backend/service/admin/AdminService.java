package org.example.backend.service.admin;

import java.util.List;
import org.example.backend.entity.admin.Admin;

public interface AdminService {
  Admin selectById(String adminId);
  List<Admin> selectAll();
  String insert(Admin admin);
  boolean update(Admin admin);
  boolean delete(String adminId);
}

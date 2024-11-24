package org.example.backend.service.admin;

import java.util.List;
import org.example.backend.entity.admin.Admin;

public interface AdminService {
  Admin selectById(String adminId);
  List<Admin> selectAll();
  String insert(Admin admin);

  boolean update(Admin admin);

  boolean updateMyEmailAndPhone(String adminId, String email, String phone);

  boolean updateMyPassword(String adminId, String password);

    boolean activateAdmin(String adminId);

  boolean banAdmin(String adminId);

  boolean delete(String adminId);

  String verifyByUsernameAndPassword(String username, String password);

  String verifyByEmailAndPassword(String email, String password);

  boolean verifyByIdAndPassword(String adminId, String password);
}

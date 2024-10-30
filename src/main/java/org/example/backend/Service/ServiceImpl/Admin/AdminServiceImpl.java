package org.example.backend.service.serviceImpl.admin;

import org.example.backend.entity.admin.Admin;
import org.example.backend.mapper.admin.AdminMapper;
import org.example.backend.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private AdminMapper adminMapper;

  @Override
  public Admin getById(String adminId) {
    return adminMapper.selectById(adminId);
  }

  @Override
  public List<Admin> getAll() {
    return adminMapper.selectList(null);
  }

  @Override
  public boolean createAdmin(Admin admin) {
    return adminMapper.insert(admin) > 0;
  }

  @Override
  public boolean updateAdmin(Admin admin) {
    return adminMapper.updateById(admin) > 0;
  }

  @Override
  public boolean deleteAdmin(String adminId) {
    return adminMapper.deleteById(adminId) > 0;
  }
}

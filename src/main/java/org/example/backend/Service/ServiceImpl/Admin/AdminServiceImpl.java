package org.example.backend.Service.ServiceImpl.Admin;

import org.example.backend.Entity.Admin.Admin;
import org.example.backend.Mapper.Admin.AdminMapper;
import org.example.backend.Service.Admin.AdminService;
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

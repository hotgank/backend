package org.example.backend.Service.ServiceImpl.Admin;

import java.util.List;
import org.example.backend.Entity.Admin.AdminLog;
import org.example.backend.Mapper.Admin.AdminLogMapper;
import org.example.backend.Service.Admin.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLogServiceImpl implements AdminLogService {
  @Autowired
  private AdminLogMapper adminLogMapper;

  @Override
  public List<AdminLog> getAllAdminLogs() {
    return adminLogMapper.selectList(null);
  }

  @Override
  public AdminLog getAdminLogById(int id) {
    return adminLogMapper.selectById(id);
  }

  @Override
  public int createAdminLog(AdminLog adminLog) {
    return adminLogMapper.insert(adminLog);
  }

  @Override
  public int updateAdminLog(AdminLog adminLog) {
    return adminLogMapper.updateById(adminLog);
  }

  @Override
  public int deleteAdminLogById(int id) {
    return adminLogMapper.deleteById(id);
  }
}

package org.example.backend.service.admin;

import java.util.List;
import org.example.backend.entity.admin.AdminLog;

public interface AdminLogService {
  List<AdminLog> getAllAdminLogs();

  AdminLog getAdminLogById(int id);

  int createAdminLog(AdminLog adminLog);

  int updateAdminLog(AdminLog adminLog);

  int deleteAdminLogById(int id);
}

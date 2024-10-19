package org.example.backend.Service.Admin;

import java.util.List;
import org.example.backend.Entity.Admin.AdminLog;

public interface AdminLogService {
  List<AdminLog> getAllAdminLogs();
  AdminLog getAdminLogById(int id);
  int createAdminLog(AdminLog adminLog);
  int updateAdminLog(AdminLog adminLog);
  int deleteAdminLogById(int id);
}

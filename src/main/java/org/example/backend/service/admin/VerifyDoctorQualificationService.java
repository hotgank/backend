package org.example.backend.service.admin;

import org.example.backend.dto.AdminGetDoctorLicenseDTO;

import java.util.List;

public interface VerifyDoctorQualificationService {
  List<AdminGetDoctorLicenseDTO> selectAll(String adminId);

  List<AdminGetDoctorLicenseDTO> selectRecent(String adminId);

  boolean approve(String auditId, String adminId, String position);

  boolean reject(String auditId, String adminId, String comment);

  int selectPendingCount(String adminId);
}

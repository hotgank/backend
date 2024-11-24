package org.example.backend.service.admin;

import org.example.backend.dto.AdminGetDoctorLicenseDTO;

import java.util.List;

public interface VerifyDoctorQualificationService {
    List<AdminGetDoctorLicenseDTO> selectAll();

    boolean approve(String auditId, String adminId);

    boolean reject(String auditId, String adminId, String comment);
}

package org.example.backend.service.admin;

import org.example.backend.dto.AdminGetDoctorLicenseDTO;

import java.util.List;

public interface VerifyDoctorQualificationService {
    List<AdminGetDoctorLicenseDTO> selectAll();


    List<AdminGetDoctorLicenseDTO> selectRecent();

    boolean approve(String auditId, String adminId, String position);

    boolean reject(String auditId, String adminId, String comment);
}

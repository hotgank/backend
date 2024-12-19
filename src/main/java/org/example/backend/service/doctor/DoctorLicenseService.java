package org.example.backend.service.doctor;

import java.util.List;

import org.example.backend.entity.admin.LicenseCheck;

public interface DoctorLicenseService {

  boolean insertCheckLicense(LicenseCheck license);

  List<LicenseCheck> selectAllCheckLicense(String doctor_id);
}

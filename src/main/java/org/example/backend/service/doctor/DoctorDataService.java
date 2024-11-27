package org.example.backend.service.doctor;

import java.util.List;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorData;
import org.example.backend.entity.admin.LicenseCheck;

public interface DoctorDataService {
  List<DoctorData> getAllDoctorData();
  DoctorData getDoctorDataById(String id);
  int createDoctorData(DoctorData doctorData);
  int updateDoctorData(DoctorData doctorData);
  int deleteDoctorDataById(String id);
  boolean insertCheckLicense(LicenseCheck license);
  List<LicenseCheck> selectAllCheckLicense(String doctor_id);


}

package org.example.backend.Service.Doctor;

import java.util.List;
import org.example.backend.Entity.Doctor.DoctorData;

public interface DoctorDataService {
  List<DoctorData> getAllDoctorData();
  DoctorData getDoctorDataById(String id);
  int createDoctorData(DoctorData doctorData);
  int updateDoctorData(DoctorData doctorData);
  int deleteDoctorDataById(String id);
}

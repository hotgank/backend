package org.example.backend.service.doctor;

import java.util.List;
import org.example.backend.entity.doctor.DoctorChildRelation;
import org.example.backend.entity.user.Child;

public interface DoctorChildRelationService {

  List<Child> selectMyPatients(String doctorId, String relationStatus);

  List<Child> selectRecentPatients(String doctorId, String relationStatus);

  int createDoctorChildRelation(DoctorChildRelation relation);

  boolean updateDoctorChildRelation(DoctorChildRelation relation);

  boolean deleteDoctorChildRelation(DoctorChildRelation relation);
}

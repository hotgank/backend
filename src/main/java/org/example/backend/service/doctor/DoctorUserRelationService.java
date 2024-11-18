package org.example.backend.service.doctor;

import java.util.List;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.user.User;

public interface DoctorUserRelationService {

  List<User> selectMyPatients(String doctorId, String relationStatus);

  List<User> selectRecentPatients(String doctorId, String relationStatus);

  List<Doctor> selectMyDoctors(String userId);

  int createDoctorUserRelation(DoctorUserRelation relation);

  boolean updateDoctorUserRelation(DoctorUserRelation relation);

  boolean deleteDoctorUserRelation(DoctorUserRelation relation);

  List<DoctorUserRelation> selectPendingPatients(String doctorId, String relationStatus);

  DoctorUserRelation selectDoctorUserRelationByIDs(String doctorId, String userId);
}

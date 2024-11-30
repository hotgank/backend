package org.example.backend.service.doctor;

import java.util.List;
import org.example.backend.dto.DoctorGetUserBindingDTO;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.others.DoctorWithStatus;
import org.example.backend.entity.user.User;

public interface DoctorUserRelationService {

  List<User> selectMyPatients(String doctorId, String relationStatus);

  List<User> selectRecentPatients(String doctorId, String relationStatus);

  List<Doctor> selectMyDoctors(String userId);

  List<DoctorWithStatus> selectPendingDoctors(String userId);

  int createDoctorUserRelation(DoctorUserRelation relation);

  boolean updateDoctorUserRelation(DoctorUserRelation relation);

  boolean deleteDoctorUserRelation(DoctorUserRelation relation);

  List<DoctorGetUserBindingDTO> selectPendingPatients(String doctorId, String relationStatus);

  DoctorUserRelation selectDoctorUserRelationByIDs(String doctorId, String userId);

  public List<DoctorUserRelation> getRelationsByDoctorId(String doctorId);

  public List<DoctorUserRelation> getRelationsByUserId(String userId);

  public DoctorUserRelation getRelationById(Integer relationId);

  List<DoctorGetUserBindingDTO> selectRemoveBindingRelations(String doctorId);
}

package org.example.backend.Service.Doctor;

import java.util.List;
import org.example.backend.Entity.Doctor.DoctorChildRelation;

public interface DoctorChildRelationService {
  List<DoctorChildRelation> getAllDoctorChildRelations();
  DoctorChildRelation getDoctorChildRelationById(int id);
  int createDoctorChildRelation(DoctorChildRelation relation);
  int updateDoctorChildRelation(DoctorChildRelation relation);
  int deleteDoctorChildRelationById(int id);
}

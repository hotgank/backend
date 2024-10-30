package org.example.backend.service.doctor;

import java.util.List;
import org.example.backend.entity.doctor.DoctorChildRelation;

public interface DoctorChildRelationService {
  List<DoctorChildRelation> getAllDoctorChildRelations();
  DoctorChildRelation getDoctorChildRelationById(int id);
  int createDoctorChildRelation(DoctorChildRelation relation);
  int updateDoctorChildRelation(DoctorChildRelation relation);
  int deleteDoctorChildRelationById(int id);
}

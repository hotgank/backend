package org.example.backend.service.serviceImpl.doctor;

import java.util.List;
import org.example.backend.entity.doctor.DoctorChildRelation;
import org.example.backend.mapper.doctor.DoctorChildRelationMapper;
import org.example.backend.service.doctor.DoctorChildRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorChildRelationServiceImpl implements DoctorChildRelationService {
  @Autowired
  private DoctorChildRelationMapper doctorChildRelationMapper;

  @Override
  public List<DoctorChildRelation> getAllDoctorChildRelations() {
    return doctorChildRelationMapper.selectList(null);
  }

  @Override
  public DoctorChildRelation getDoctorChildRelationById(int id) {
    return doctorChildRelationMapper.selectById(id);
  }

  @Override
  public int createDoctorChildRelation(DoctorChildRelation relation) {
    return doctorChildRelationMapper.insert(relation);
  }

  @Override
  public int updateDoctorChildRelation(DoctorChildRelation relation) {
    return doctorChildRelationMapper.updateById(relation);
  }

  @Override
  public int deleteDoctorChildRelationById(int id) {
    return doctorChildRelationMapper.deleteById(id);
  }
}

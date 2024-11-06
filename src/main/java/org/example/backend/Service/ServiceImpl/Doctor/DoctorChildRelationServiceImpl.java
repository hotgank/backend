package org.example.backend.Service.ServiceImpl.Doctor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.example.backend.Service.ServiceImpl.Doctor.DoctorServiceImpl;
import org.example.backend.entity.doctor.DoctorChildRelation;
import org.example.backend.entity.user.Child;
import org.example.backend.mapper.doctor.DoctorChildRelationMapper;
import org.example.backend.service.doctor.DoctorChildRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorChildRelationServiceImpl implements DoctorChildRelationService {

  private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

  @Autowired
  private DoctorChildRelationMapper doctorChildRelationMapper;

  @Override
  public List<Child> selectMyPatients(DoctorChildRelation relation) {
    try {
      String doctorId = relation.getDoctorId();
      List<Child> myPatients = doctorChildRelationMapper.selectMyPatients(doctorId);
      logger.info("获取我的患者成功");
      return myPatients;
    } catch (Exception e) {
      logger.error("获取我的患者失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public boolean createDoctorChildRelation(DoctorChildRelation relation) {
    try {
      relation.setCreatedAt(LocalDateTime.now());
      int relationId = doctorChildRelationMapper.createDoctorChildRelation(relation);
      logger.info("Relation with ID {} updated successfully", relationId);
      return true;
    } catch (Exception e) {
      logger.error("Error creating relation: {}", e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean deleteDoctorChildRelation(DoctorChildRelation relation) {
    try {
      int deletedRows = doctorChildRelationMapper.deleteDoctorChildRelation(relation);
      logger.info("{} rows deleted from relation table", deletedRows);
      return true;
    } catch (Exception e) {
      logger.error("Error deleting relation: {}", e.getMessage(), e);
      return false;
    }
  }
}

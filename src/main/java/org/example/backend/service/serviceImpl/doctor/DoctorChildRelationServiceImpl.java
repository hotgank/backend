package org.example.backend.service.serviceImpl.doctor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

  private static final Logger logger = LoggerFactory.getLogger(DoctorChildRelationServiceImpl.class);

  @Autowired
  private DoctorChildRelationMapper doctorChildRelationMapper;

  @Override
  public List<Child> selectMyPatients(String doctorId, String relationStatus) {
    try {
      List<Child> myPatients = doctorChildRelationMapper.selectMyPatients(doctorId, relationStatus);
      logger.info("获取我的患者成功");
      return myPatients;
    } catch (Exception e) {
      logger.error("获取我的患者失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<Child> selectRecentPatients(String doctorId, String relationStatus) {
    try {
      List<Child> myPatients = doctorChildRelationMapper.selectRecentPatients(doctorId, relationStatus);
      logger.info("获取最近患者成功");
      return myPatients;
    } catch (Exception e) {
      logger.error("获取最近患者失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public int createDoctorChildRelation(DoctorChildRelation relation) {
    try {
      relation.setCreatedAt(LocalDateTime.now());
      relation.setRelationStatus("pending");
      int result = doctorChildRelationMapper.createDoctorChildRelation(relation);
      if(result > 0){
        int relationId = relation.getRelationId();
        logger.info("Relation with ID {} updated successfully", relationId);
        return relationId;
      }else{
        logger.error("Error creating relation");
        return 0;
      }
    } catch (Exception e) {
      logger.error("Error creating relation: {}", e.getMessage(), e);
      return 0;
    }
  }

  @Override
  public boolean updateDoctorChildRelation(DoctorChildRelation relation) {
    try {
      boolean updated = doctorChildRelationMapper.updateDoctorChildRelation(relation);
      if (updated) {
        logger.info("DoctorChildRelation with ID {} updated successfully", relation.getRelationId());
      } else {
        logger.error("Error updating relation");
      }
      return updated;
    } catch (Exception e) {
      logger.error("Error updating relation: {}", e.getMessage(),e);
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

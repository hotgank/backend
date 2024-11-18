package org.example.backend.service.serviceImpl.doctor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.user.Child;
import org.example.backend.entity.user.User;
import org.example.backend.mapper.doctor.DoctorUserRelationMapper;
import org.example.backend.service.doctor.DoctorUserRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorUserRelationServiceImpl implements DoctorUserRelationService {

  private static final Logger logger = LoggerFactory.getLogger(DoctorUserRelationServiceImpl.class);

  @Autowired
  private DoctorUserRelationMapper doctorUserRelationMapper;

  @Override
  public List<User> selectMyPatients(String doctorId, String relationStatus) {
    try {
      List<User> myPatients = doctorUserRelationMapper.selectMyPatients(doctorId, relationStatus);
      logger.info("获取我的患者成功");
      return myPatients;
    } catch (Exception e) {
      logger.error("获取我的患者失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<User> selectRecentPatients(String doctorId, String relationStatus) {
    try {
      List<User> myPatients = doctorUserRelationMapper.selectRecentPatients(doctorId, relationStatus);
      logger.info("获取最近患者成功");
      return myPatients;
    } catch (Exception e) {
      logger.error("获取最近患者失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public int createDoctorUserRelation(DoctorUserRelation relation) {
    try {
      relation.setCreatedAt(LocalDateTime.now());
      relation.setRelationStatus("pending");
      int result = doctorUserRelationMapper.createDoctorUserRelation(relation);
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
  public boolean updateDoctorUserRelation(DoctorUserRelation relation) {
    try {
      boolean updated = doctorUserRelationMapper.updateDoctorUserRelation(relation);
      if (updated) {
        logger.info("DoctorUserRelation with ID {} updated successfully", relation.getRelationId());
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
  public boolean deleteDoctorUserRelation(DoctorUserRelation relation) {
    try {
      int deletedRows = doctorUserRelationMapper.deleteDoctorUserRelation(relation);
      logger.info("{} rows deleted from relation table", deletedRows);
      return true;
    } catch (Exception e) {
      logger.error("Error deleting relation: {}", e.getMessage(), e);
      return false;
    }
  }

  @Override
  public List<DoctorUserRelation> selectPendingPatients(String doctorId, String relationStatus) {
    try {
      List<DoctorUserRelation> pendingPatients = doctorUserRelationMapper.selectPendingPatients(doctorId, relationStatus);
      logger.info("获取待绑定患者成功");
      return pendingPatients;
    } catch (Exception e) {
      logger.error("获取待绑定患者失败", e);
      return Collections.emptyList();
    }
  }
}

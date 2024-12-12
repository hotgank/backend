package org.example.backend.service.serviceImpl.others;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.others.Consultation;
import org.example.backend.mapper.doctor.DoctorMapper;
import org.example.backend.mapper.doctor.DoctorUserRelationMapper;
import org.example.backend.mapper.others.ConsultationMapper;
import org.example.backend.mapper.user.UserMapper;
import org.example.backend.service.others.ConsultationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationServiceImpl implements ConsultationService {

  private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

  @Autowired private ConsultationMapper consultationMapper;

  @Autowired private UserMapper userMapper;

  @Autowired private DoctorMapper doctorMapper;

  @Autowired private DoctorUserRelationMapper doctorUserRelationMapper;

  @Override
  public List<Consultation> selectAllConsultations() {
    return consultationMapper.selectAll();
  }

  @Override
  public Consultation selectConsultationById(int consultationId) {
    return consultationMapper.selectById(consultationId);
  }

  @Override
  public List<Consultation> selectConsultationByDoctorId(String doctorId) {
    try {
      return consultationMapper.selectByDoctorId(doctorId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取咨询信息失败, doctorId: {}", doctorId, e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<Consultation> selectConsultationByUserId(String userId) {
    try {
      return consultationMapper.selectByUserId(userId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取咨询信息失败, userId: {}", userId, e);
      return Collections.emptyList();
    }
  }

  @Override
  public Consultation selectConsultationByDoctorIdAndUserId(String doctorId, String userId) {
    try {
      return consultationMapper.selectConsultationByDoctorIdAndUserId(doctorId, userId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取咨询信息失败, doctorId: {}, userId: {}", doctorId, userId, e);
      return null;
    }
  }

  @Override
  public int insertConsultation(String doctorId, String userId, int rating) {
    try {
      //根据关系id查询医生id和用户id
      DoctorUserRelation relation = doctorUserRelationMapper.selectDoctorUserRelation(doctorId, userId);
      if (relation == null){
        logger.error("未找到医生和用户关系, doctorId: {}, userId: {}", doctorId, userId);
      }
      LocalDateTime createdAt = relation.getCreatedAt();

      Consultation consultation = new Consultation();
      consultation.setDoctorId(doctorId);
      consultation.setUserId(userId);
      consultation.setConsultationStart(createdAt);
      consultation.setConsultationEnd(LocalDateTime.now());
      //判断分数是否在1-5之间
      if (rating < 1 || rating > 5) {
        logger.error("分数不在1-5之间, rating: {}", rating);
        return 0;
      }
      consultation.setRating(rating);
      consultationMapper.insertConsultation(consultation);
      float avgRating = consultationMapper.selectAvgRatingByDoctorId(doctorId);
      if(avgRating <= 5 && avgRating >= 0) {
        Doctor doctor = doctorMapper.selectById(doctorId);
        doctor.setRating(avgRating);
        doctorMapper.updateDoctor(doctor);
      }
      return consultation.getConsultationId();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("创建失败 e");
      return 0;
    }
  }

  @Override
  public float selectAvgRatingByDoctorId(String doctorId) {
    return consultationMapper.selectAvgRatingByDoctorId(doctorId);
  }
}

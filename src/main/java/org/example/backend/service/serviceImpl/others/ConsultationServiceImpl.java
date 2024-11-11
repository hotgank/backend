package org.example.backend.service.serviceImpl.others;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.example.backend.entity.others.Consultation;
import org.example.backend.mapper.others.ConsultationMapper;
import org.example.backend.service.others.ConsultationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationServiceImpl implements ConsultationService {

  private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

  @Autowired
  private ConsultationMapper consultationMapper;

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
  public int insertConsultation(String doctorId, String userId) {
    try {
      Consultation consultation = new Consultation();
      consultation.setDoctorId(doctorId);
      consultation.setUserId(userId);
      consultation.setConsultationStart(LocalDateTime.now());
      return consultationMapper.insertConsultation(consultation);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("创建咨询失败, doctorId: {}, userId: {}",
          doctorId, userId, e);
      return 0;
    }
  }
}

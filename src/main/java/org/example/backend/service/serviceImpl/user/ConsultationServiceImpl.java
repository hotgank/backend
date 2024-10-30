package org.example.backend.service.serviceImpl.user;

import java.util.List;
import org.example.backend.entity.user.Consultation;
import org.example.backend.mapper.user.ConsultationMapper;
import org.example.backend.service.user.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationServiceImpl implements ConsultationService {
  @Autowired
  private ConsultationMapper consultationMapper;

  @Override
  public List<Consultation> getAllConsultations() {
    return consultationMapper.selectAll();
  }

  @Override
  public Consultation getConsultationById(int id) {
    return consultationMapper.selectById(id);
  }

  @Override
  public int createConsultation(Consultation consultation) {
    return consultationMapper.insert(consultation);
  }

  @Override
  public int updateConsultation(Consultation consultation) {
    return consultationMapper.updateById(consultation);
  }

  @Override
  public int deleteConsultationById(int id) {
    return consultationMapper.deleteById(id);
  }
}

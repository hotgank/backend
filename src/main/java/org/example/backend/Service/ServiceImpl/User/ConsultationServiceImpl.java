package org.example.backend.Service.ServiceImpl.User;

import java.util.List;
import org.example.backend.Entity.User.Consultation;
import org.example.backend.Mapper.User.ConsultationMapper;
import org.example.backend.Service.User.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationServiceImpl implements ConsultationService {
  @Autowired
  private ConsultationMapper consultationMapper;

  @Override
  public List<Consultation> getAllConsultations() {
    return consultationMapper.selectList(null);
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

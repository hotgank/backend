package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.Consultation;

public interface ConsultationService {
  List<Consultation> getAllConsultations();
  Consultation getConsultationById(int id);
  int createConsultation(Consultation consultation);
  int updateConsultation(Consultation consultation);
  int deleteConsultationById(int id);
}

package org.example.backend.Service.User;

import java.util.List;
import org.example.backend.Entity.User.Consultation;

public interface ConsultationService {
  List<Consultation> getAllConsultations();
  Consultation getConsultationById(int id);
  int createConsultation(Consultation consultation);
  int updateConsultation(Consultation consultation);
  int deleteConsultationById(int id);
}

package org.example.backend.service.others;

import java.time.LocalDateTime;
import java.util.List;
import org.example.backend.entity.others.Consultation;

public interface ConsultationService {
  List<Consultation> selectAllConsultations();

  Consultation selectConsultationById(int id);

  List<Consultation> selectConsultationByDoctorId(String doctorId);

  List<Consultation> selectConsultationByUserId(String userId);

  Consultation selectConsultationByDoctorIdAndUserId(String doctorId, String userId);

  int insertConsultation(int relationId, int rating);
}

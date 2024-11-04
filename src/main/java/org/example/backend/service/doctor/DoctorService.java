package org.example.backend.service.doctor;

import java.util.List;
import org.example.backend.entity.doctor.Doctor;

public interface DoctorService {
  Doctor selectById(String doctorId);
  List<Doctor> selectAll();
  String insert(Doctor doctor);
  boolean update(Doctor doctor);
  boolean delete(String doctorId);

  String generateRegisterCode(String email);

  boolean registerDoctor(Doctor doctor);
}

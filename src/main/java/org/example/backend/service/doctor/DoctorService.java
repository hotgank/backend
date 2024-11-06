package org.example.backend.Service.doctor;

import java.util.List;
import org.example.backend.entity.doctor.Doctor;

public interface DoctorService {
  Doctor selectById(String doctorId);
  List<Doctor> selectAll();
  String insert(Doctor doctor);
  boolean update(Doctor doctor);
  boolean delete(String doctorId);

  boolean updatePassword(String doctorId, String newPassword);

  String generateRegisterCode(String email);

  boolean registerDoctor(Doctor doctor);

  boolean validatePassword(String doctorId, String password);

  boolean validateRegisterCode(String email, String registerCode);
}

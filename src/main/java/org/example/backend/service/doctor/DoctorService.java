package org.example.backend.service.doctor;

import java.util.List;
import org.example.backend.entity.doctor.Doctor;

public interface DoctorService {
  Doctor getById(String doctorId);
  List<Doctor> getAll();
  boolean createDoctor(Doctor doctor);
  boolean updateDoctor(Doctor doctor);
  boolean deleteDoctor(String doctorId);
}

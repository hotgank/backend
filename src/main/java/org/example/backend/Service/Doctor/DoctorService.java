package org.example.backend.Service.Doctor;

import java.util.List;
import org.example.backend.Entity.Doctor.Doctor;

public interface DoctorService {
  Doctor getById(String doctorId);
  List<Doctor> getAll();
  boolean createDoctor(Doctor doctor);
  boolean updateDoctor(Doctor doctor);
  boolean deleteDoctor(String doctorId);
}

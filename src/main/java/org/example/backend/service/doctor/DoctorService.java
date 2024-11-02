package org.example.backend.Service.doctor;

import org.example.backend.entity.doctor.Doctor;

import java.util.List;

public interface DoctorService {
  Doctor getById(String doctorId);
  List<Doctor> getAll();
  boolean createDoctor(Doctor doctor);
  boolean updateDoctor(Doctor doctor);
  boolean deleteDoctor(String doctorId);
  String generateRegisterCode(String email);
  boolean registerDoctor(Doctor doctor);
}

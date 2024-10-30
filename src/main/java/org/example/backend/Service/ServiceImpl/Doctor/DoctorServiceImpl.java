package org.example.backend.service.serviceImpl.doctor;

import org.example.backend.entity.doctor.Doctor;
import org.example.backend.mapper.doctor.DoctorMapper;
import org.example.backend.service.doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

  @Autowired
  private DoctorMapper doctorMapper;

  @Override
  public Doctor getById(String doctorId) {
    return doctorMapper.selectById(doctorId);
  }

  @Override
  public List<Doctor> getAll() {
    return doctorMapper.selectList(null);
  }

  @Override
  public boolean createDoctor(Doctor doctor) {
    return doctorMapper.insert(doctor) > 0;
  }

  @Override
  public boolean updateDoctor(Doctor doctor) {
    return doctorMapper.updateById(doctor) > 0;
  }

  @Override
  public boolean deleteDoctor(String doctorId) {
    return doctorMapper.deleteById(doctorId) > 0;
  }
}

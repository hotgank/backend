package org.example.backend.Service.ServiceImpl.Doctor;

import org.example.backend.entity.doctor.Doctor;

import org.example.backend.mapper.doctor.DoctorMapper;
import org.example.backend.Service.doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

  @Override
  public String generateRegisterCode(String email) {
    // 生成一个唯一的注册码
    String registerCode = UUID.randomUUID().toString().substring(0, 8);
    // 存储注册码到数据库或其他存储方式
    // 这里假设有一个方法可以存储注册码，例如：
    // registerCodeRepository.save(new RegisterCode(email, registerCode));
    return registerCode;
  }

  @Override
  public boolean registerDoctor(Doctor doctor) {
//    // 检查注册码是否有效
//    String email = request.getEmail();
//    String registerCode = request.getRegisterCode();
//    // 假设有一个方法可以验证注册码，例如：
//    // boolean isValid = registerCodeRepository.isValid(email, registerCode);
//    boolean isValid = true; // 这里假设注册码总是有效的，实际应用中需要验证
//
//    if (!isValid) {
      return false;
//    }
//
//    // 创建医生对象
//    Doctor doctor = new Doctor();
//    doctor.setName(request.getName());
//    doctor.setEmail(email);
//    doctor.setPassword(request.getPassword());
//    doctor.setRegisterCode(registerCode);
//
//    // 插入医生信息到数据库
//    return createDoctor(doctor);
  }
}

package org.example.backend.service.serviceImpl.doctor;

import java.util.List;
import org.example.backend.entity.admin.LicenseCheck;
import org.example.backend.entity.doctor.DoctorData;
import org.example.backend.mapper.doctor.DoctorDataMapper;
import org.example.backend.mapper.admin.LicenseCheckMapper;
import org.example.backend.service.doctor.DoctorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorDataServiceImpl implements DoctorDataService {
  @Autowired private DoctorDataMapper doctorDataMapper;

  @Autowired private LicenseCheckMapper licenseCheckMapper;

  @Override
  public List<DoctorData> getAllDoctorData() {
    return doctorDataMapper.selectList(null);
  }

  @Override
  public DoctorData getDoctorDataById(String id) {
    return doctorDataMapper.selectById(id);
  }

  @Override
  public int createDoctorData(DoctorData doctorData) {
    return doctorDataMapper.insert(doctorData);
  }

  @Override
  public int updateDoctorData(DoctorData doctorData) {
    return doctorDataMapper.updateById(doctorData);
  }

  @Override
  public int deleteDoctorDataById(String id) {
    return doctorDataMapper.deleteById(id);
  }

  @Override
  public boolean insertCheckLicense(LicenseCheck license) {
    return licenseCheckMapper.insert(license);
  }

  @Override
  public List<LicenseCheck> selectAllCheckLicense(String doctor_id) {
    return licenseCheckMapper.selectByDoctorId(doctor_id);
  }
}

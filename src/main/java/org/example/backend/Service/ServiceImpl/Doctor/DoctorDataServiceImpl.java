package org.example.backend.Service.ServiceImpl.Doctor;

import java.util.List;
import org.example.backend.entity.doctor.DoctorData;
import org.example.backend.mapper.doctor.DoctorDataMapper;
import org.example.backend.Service.doctor.DoctorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorDataServiceImpl implements DoctorDataService {
  @Autowired
  private DoctorDataMapper doctorDataMapper;

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
}

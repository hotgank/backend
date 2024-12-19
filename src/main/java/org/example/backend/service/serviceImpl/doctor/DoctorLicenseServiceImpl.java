package org.example.backend.service.serviceImpl.doctor;

import java.util.List;
import org.example.backend.entity.admin.LicenseCheck;
import org.example.backend.mapper.admin.LicenseCheckMapper;
import org.example.backend.mapper.doctor.DoctorMapper;
import org.example.backend.service.doctor.DoctorLicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorLicenseServiceImpl implements DoctorLicenseService {

  private static final Logger logger = LoggerFactory.getLogger(DoctorLicenseServiceImpl.class);

  @Autowired private LicenseCheckMapper licenseCheckMapper;

  @Autowired private DoctorMapper doctorMapper;

  @Override
  public boolean insertCheckLicense(LicenseCheck license) {
    try {
      String doctorId = license.getDoctorId();

      if(!doctorMapper.updateDoctorQualification(doctorId, "未认证")){
        logger.error("更新医生账户认证信息失败");
      }
      if(!licenseCheckMapper.deletePendingByDoctorId(doctorId)){
        logger.error("医生删除未审核的账户认证记录失败");
      }
      if(!licenseCheckMapper.insert(license)){
        logger.error("插入账户认证记录失败");
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public List<LicenseCheck> selectAllCheckLicense(String doctor_id) {
    return licenseCheckMapper.selectByDoctorId(doctor_id);
  }
}

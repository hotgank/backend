package org.example.backend.service.serviceImpl.others;

import java.util.List;
import org.example.backend.entity.others.Hospital;
import org.example.backend.mapper.others.HospitalMapper;
import org.example.backend.service.others.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl implements HospitalService {

  private static final Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

  @Autowired private HospitalMapper hospitalMapper;

  @Override
  public List<Hospital> selectAllHospitals() {
    try {
      return hospitalMapper.selectAll();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取医院列表失败", e);
      return null;
    }
  }
}

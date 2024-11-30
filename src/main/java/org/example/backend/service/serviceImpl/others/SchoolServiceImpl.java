package org.example.backend.service.serviceImpl.others;

import java.util.List;
import org.example.backend.entity.others.School;
import org.example.backend.mapper.others.SchoolMapper;
import org.example.backend.service.others.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {

  private static final Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);

  @Autowired private SchoolMapper schoolMapper;

  @Override
  public List<School> selectAllSchools() {
    try {
      return schoolMapper.selectAll();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取学校列表失败", e);
      return null;
    }
  }
}

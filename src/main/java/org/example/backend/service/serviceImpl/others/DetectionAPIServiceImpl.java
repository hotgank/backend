package org.example.backend.service.serviceImpl.others;


import java.util.List;
import org.example.backend.entity.others.DetectionAPI;
import org.example.backend.mapper.others.DetectionAPIMapper;
import org.example.backend.service.others.DetectionAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetectionAPIServiceImpl implements DetectionAPIService {

  @Autowired private DetectionAPIMapper detectionAPIMapper;

  @Override
  public int deleteDetectionAPIById(int id) {
    return detectionAPIMapper.deleteDetectionAPIById(id);
  }

  @Override
  public int insertDetectionAPI(DetectionAPI detectionAPI) {
    //检查apiType是否为空
    if (detectionAPI.getApiType() == null) {
      return 0;
    }
    //检查是否重复
    if (detectionAPIMapper.selectByType(detectionAPI.getApiType()) != null) {
      return 0;
    }
    return detectionAPIMapper.insertDetectionAPI(detectionAPI);
  }

  @Override

  public List<DetectionAPI> selectAll() {
    return detectionAPIMapper.selectAll();
  }

  @Override
  public int updateDetectionAPI(DetectionAPI detectionAPI) {
    return detectionAPIMapper.updateDetectionAPI(detectionAPI);
  }

  @Override
  public DetectionAPI selectByType(String apiType) {
    return detectionAPIMapper.selectByType(apiType);
  }
}

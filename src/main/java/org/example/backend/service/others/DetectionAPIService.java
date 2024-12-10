package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.DetectionAPI;

public interface DetectionAPIService {

  int insertDetectionAPI(DetectionAPI detectionAPI);

  int deleteDetectionAPIById(int id);

  List<DetectionAPI> selectAll();

  int updateDetectionAPI(DetectionAPI detectionAPI);

  DetectionAPI selectByType(String apiType);
}

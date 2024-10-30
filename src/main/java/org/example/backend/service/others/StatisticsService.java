package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.Statistics;

public interface StatisticsService {
  Statistics getById(Integer statId);
  List<Statistics> getAll();
  boolean createStatistics(Statistics statistics);
  boolean updateStatistics(Statistics statistics);
  boolean deleteStatistics(Integer statId);
}

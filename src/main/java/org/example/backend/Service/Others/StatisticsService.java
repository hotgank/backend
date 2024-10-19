package org.example.backend.Service.Others;

import java.util.List;
import org.example.backend.Entity.Others.Statistics;

public interface StatisticsService {
  Statistics getById(Integer statId);
  List<Statistics> getAll();
  boolean createStatistics(Statistics statistics);
  boolean updateStatistics(Statistics statistics);
  boolean deleteStatistics(Integer statId);
}

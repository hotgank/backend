package org.example.backend.service.serviceImpl.others;

import org.example.backend.entity.others.Statistics;
import org.example.backend.mapper.others.StatisticsMapper;
import org.example.backend.service.others.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

  @Autowired private StatisticsMapper statisticsMapper;

  @Override
  public Statistics getById(Integer statId) {
    return statisticsMapper.selectById(statId);
  }

  @Override
  public List<Statistics> getAll() {
    return statisticsMapper.selectList(null);
  }

  @Override
  public boolean createStatistics(Statistics statistics) {
    return statisticsMapper.insert(statistics) > 0;
  }

  @Override
  public boolean updateStatistics(Statistics statistics) {
    return statisticsMapper.updateById(statistics) > 0;
  }

  @Override
  public boolean deleteStatistics(Integer statId) {
    return statisticsMapper.deleteById(statId) > 0;
  }
}

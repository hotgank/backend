package org.example.backend.service.serviceImpl.others;

import org.example.backend.entity.others.HealthArticle;
import org.example.backend.mapper.others.HealthArticleMapper;
import org.example.backend.service.others.HealthArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthArticleServiceImpl implements HealthArticleService {

  @Autowired
  private HealthArticleMapper healthArticleMapper;

  @Override
  public HealthArticle getById(Integer articleId) {
    return healthArticleMapper.selectById(articleId);
  }

  @Override
  public List<HealthArticle> getAll() {
    return healthArticleMapper.selectList(null);
  }

  @Override
  public boolean createHealthArticle(HealthArticle healthArticle) {
    return healthArticleMapper.insert(healthArticle) > 0;
  }

  @Override
  public boolean updateHealthArticle(HealthArticle healthArticle) {
    return healthArticleMapper.updateById(healthArticle) > 0;
  }

  @Override
  public boolean deleteHealthArticle(Integer articleId) {
    return healthArticleMapper.deleteById(articleId) > 0;
  }
}

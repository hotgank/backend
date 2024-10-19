package org.example.backend.Service.Others;

import java.util.List;
import org.example.backend.Entity.Others.HealthArticle;

public interface HealthArticleService {
  HealthArticle getById(Integer articleId);
  List<HealthArticle> getAll();
  boolean createHealthArticle(HealthArticle healthArticle);
  boolean updateHealthArticle(HealthArticle healthArticle);
  boolean deleteHealthArticle(Integer articleId);
}

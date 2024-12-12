package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.HealthArticle;

public interface HealthArticleService {
  HealthArticle getById(Integer articleId);

  List<HealthArticle> getAll();

  List<HealthArticle> getTotalAll();

  boolean createHealthArticle(HealthArticle healthArticle);

  boolean updateHealthArticle(HealthArticle healthArticle);

  boolean deleteHealthArticle(Integer articleId);

  List<HealthArticle> getByDoctorId(String doctorId);

  int selectCountByDoctorId(String doctorId);
}

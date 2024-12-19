package org.example.backend.service.others;

import java.util.List;

import org.example.backend.dto.HealthArticleDetailsDTO;
import org.example.backend.dto.HealthArticleTotalListDTO;
import org.example.backend.entity.others.HealthArticle;

public interface HealthArticleService {
  HealthArticle getById(Integer articleId);

  HealthArticleDetailsDTO getDetailsById(Integer articleId);

  List<HealthArticleTotalListDTO> getAll();

  List<HealthArticleTotalListDTO> getTotalAll(String adminId);

  boolean createHealthArticle(HealthArticle healthArticle);

  boolean updateHealthArticle(HealthArticle healthArticle);

  boolean deleteHealthArticle(Integer articleId);

  List<HealthArticle> getByDoctorId(String doctorId);

  int selectCountByDoctorId(String doctorId);
}

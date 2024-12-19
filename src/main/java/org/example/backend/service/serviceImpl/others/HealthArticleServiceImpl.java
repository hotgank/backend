package org.example.backend.service.serviceImpl.others;

import org.example.backend.dto.HealthArticleDetailsDTO;
import org.example.backend.dto.HealthArticleTotalListDTO;
import org.example.backend.entity.others.HealthArticle;
import org.example.backend.mapper.others.HealthArticleMapper;
import org.example.backend.service.others.HealthArticleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthArticleServiceImpl implements HealthArticleService {

  private static final Logger logger = LoggerFactory.getLogger(HealthArticleServiceImpl.class);

  @Autowired private HealthArticleMapper healthArticleMapper;

  @Override
  public HealthArticle getById(Integer articleId) {
    return healthArticleMapper.selectById(articleId);
  }

  @Override
  public HealthArticleDetailsDTO getDetailsById(Integer articleId) {
    return healthArticleMapper.selectDetailsById(articleId);
  }

  @Override
  public List<HealthArticleTotalListDTO> getAll() {
    return healthArticleMapper.selectList();
  }

  @Override
  public List<HealthArticleTotalListDTO> getTotalAll() {
    return healthArticleMapper.selectListAll();
  }

  @Override
  public boolean createHealthArticle(HealthArticle healthArticle) {
    try {
      healthArticleMapper.insertHealthArticle(healthArticle);
      logger.info("Health article with ID {} created successfully", healthArticle.getArticleId());
      return true;
    } catch (Exception e) {
      logger.error(
          "Error creating health article with ID {}: {}",
          healthArticle.getArticleId(),
          e.getMessage(),
          e);
      return false;
    }
  }

  @Override
  public boolean updateHealthArticle(HealthArticle healthArticle) {
    try {
      healthArticleMapper.updateById(healthArticle);
      logger.info("Health article with ID {} updated successfully", healthArticle.getArticleId());
      return true;
    } catch (Exception e) {
      logger.error(
          "Error updating health article with ID {}: {}",
          healthArticle.getArticleId(),
          e.getMessage(),
          e);
      return false;
    }
  }

  @Override
  public boolean deleteHealthArticle(Integer articleId) {
    try {
      healthArticleMapper.deleteById(articleId);
      logger.info("Health article with ID {} deleted successfully", articleId);
      return true;
    } catch (Exception e) {
      logger.error("Error deleting health article with ID {}: {}", articleId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public List<HealthArticle> getByDoctorId(String doctorId) {
    return healthArticleMapper.selectByDoctorId(doctorId);
  }

  @Override
  public int selectCountByDoctorId(String doctorId) {
    return healthArticleMapper.selectCountByDoctorId(doctorId);
  }
}

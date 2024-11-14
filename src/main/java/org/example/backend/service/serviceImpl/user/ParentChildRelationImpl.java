package org.example.backend.service.serviceImpl.user;

import java.util.List;
import org.example.backend.entity.user.ParentChildRelation;
import org.example.backend.mapper.user.ParentChildRelationMapper;
import org.example.backend.service.user.ParentChildRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ParentChildRelationImpl
 */
@Service
public class ParentChildRelationImpl implements ParentChildRelationService {

  @Autowired
  private ParentChildRelationMapper parentChildRelationMapper;

  private static final Logger logger = LoggerFactory.getLogger( ParentChildRelationImpl.class);
  @Override
  public List<ParentChildRelation> getAllRelations() {
    return parentChildRelationMapper.selectAll();
  }

  @Override
  public ParentChildRelation getRelationById(int relationId) {
    return parentChildRelationMapper.selectById(relationId);
  }

  @Override
  public int createRelation(ParentChildRelation relation) {
    try {
      logger.info("relation created successfully");
      parentChildRelationMapper.insertRelation(relation);
      return relation.getRelationId();
    } catch (Exception e) {
      logger.error("Error creating relation: {}", e.getMessage(), e);
      return -1;
    }

  }

  @Override
  public Boolean updateRelation(ParentChildRelation relation) {
    try {
      parentChildRelationMapper.updateRelation(relation);
      logger.info("relation updated successfully");
      return true;
    } catch (Exception e) {
      logger.error("Error updating relation: {}", e.getMessage(), e);
      return false;
    }
  }

  @Override
  public Boolean deleteRelationById( int relationId) {
    try {
      parentChildRelationMapper.deleteRelationById(relationId);
      logger.info("relation deleted successfully");
      return true;
    } catch (Exception e) {
      logger.error("Error deleting relation: {}", e.getMessage(), e);
      return false;
    }
    }

    @Override
  public List<ParentChildRelation> getRelationsByUserId(String userId) {
    try {
      return parentChildRelationMapper.selectByUserId(userId);
    } catch (Exception e) {
      logger.error("Error getting relations by userId: {}", e.getMessage(), e);
      return null;
    }
    }

    // delete all relations by childId
    @Override
  public Boolean deleteRelationsByChildId(String childId) {
    try {
      parentChildRelationMapper.deleteRelationsByChildId(childId);
      logger.info("relations deleted successfully");
      return true;
    } catch (Exception e) {
      logger.error("Error deleting relations: {}", e.getMessage(), e);
      return false;
    }
  }
}

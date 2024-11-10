package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.ParentChildRelation;

public interface ParentChildRelationService {
  List<ParentChildRelation> getAllRelations();
  ParentChildRelation getRelationById(int relationId);
  int createRelation(ParentChildRelation relation);
  Boolean updateRelation(ParentChildRelation relation);
  Boolean deleteRelationById(int relationId);
  List<ParentChildRelation> getRelationsByUserId(String userId);
}
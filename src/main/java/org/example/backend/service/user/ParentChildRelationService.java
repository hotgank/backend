package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.ParentChildRelation;

public interface ParentChildRelationService {
  List<ParentChildRelation> getAllRelations();
  ParentChildRelation getRelationById(int id);
  int createRelation(ParentChildRelation relation);
  int updateRelation(ParentChildRelation relation);
  int deleteRelationById(int id);
}
package org.example.backend.Service.User;

import java.util.List;
import org.example.backend.Entity.User.ParentChildRelation;

public interface ParentChildRelationService {
  List<ParentChildRelation> getAllRelations();
  ParentChildRelation getRelationById(int id);
  int createRelation(ParentChildRelation relation);
  int updateRelation(ParentChildRelation relation);
  int deleteRelationById(int id);
}
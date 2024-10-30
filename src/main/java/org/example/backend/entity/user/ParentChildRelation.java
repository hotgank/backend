package org.example.backend.entity.user;

import java.time.LocalDateTime;

public class ParentChildRelation {
  private int relationId;
  private String userId;  // Foreign key: User ID
  private String childId; // Foreign key: Child ID
  private String relationType;
  private LocalDateTime createdAt;

  public int getRelationId() {
    return relationId;
  }

  public String getUserId() {
    return userId;
  }

  public String getChildId() {
    return childId;
  }

  public String getRelationType() {
    return relationType;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setRelationId(int relationId) {
    this.relationId = relationId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public void setRelationType(String relationType) {
    this.relationType = relationType;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  // getters and setters
}

package org.example.backend.entity.doctor;

import java.time.LocalDateTime;

public class DoctorUserRelation {
  private int relationId;
  private String doctorId; // Foreign key: Doctor ID
  private String userId; // Foreign key: Child ID
  private String relationStatus;
  private LocalDateTime createdAt;

  public int getRelationId() {
    return relationId;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public String getUserId() {
    return userId;
  }

  public String getRelationStatus() {
    return relationStatus;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setRelationId(int relationId) {
    this.relationId = relationId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setRelationStatus(String relationStatus) {
    this.relationStatus = relationStatus;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  // getters and setters

  @Override
  public String toString() {
    return "DoctorUserRelation{"
        + "relationId="
        + relationId
        + ", doctorId='"
        + doctorId
        + '\''
        + ", userId='"
        + userId
        + '\''
        + ", relationType='"
        + relationStatus
        + '\''
        + ", createdAt="
        + createdAt
        + '}';
  }
}

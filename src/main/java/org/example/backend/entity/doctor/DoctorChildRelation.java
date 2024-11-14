package org.example.backend.entity.doctor;

import java.time.LocalDateTime;

public class DoctorChildRelation {
  private int relationId;
  private String doctorId; // Foreign key: Doctor ID
  private String childId;  // Foreign key: Child ID
  private String relationStatus;
  private LocalDateTime createdAt;

  public int getRelationId() {
    return relationId;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public String getChildId() {
    return childId;
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

  public void setChildId(String childId) {
    this.childId = childId;
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
    return "DoctorChildRelation{" +
        "relationId=" + relationId +
        ", doctorId='" + doctorId + '\'' +
        ", childId='" + childId + '\'' +
        ", relationType='" + relationStatus + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}
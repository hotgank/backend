package org.example.backend.dto;

import java.time.LocalDateTime;

public class DoctorGetUserBindingDTO {
  private int relationId;
  private String doctorId; // Foreign key: Doctor ID
  private String userId; // Foreign key: Child ID
  private String relationStatus;
  private LocalDateTime createdAt;
  private String username;

  // Getters and setters
  public int getRelationId() {
    return relationId;
  }

  public void setRelationId(int relationId) {
    this.relationId = relationId;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRelationStatus() {
    return relationStatus;
  }

  public void setRelationStatus(String relationStatus) {
    this.relationStatus = relationStatus;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}

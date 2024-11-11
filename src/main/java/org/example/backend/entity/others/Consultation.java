package org.example.backend.entity.others;

import java.time.LocalDateTime;

public class Consultation {
  private int consultationId;
  private String doctorId; // Foreign key: Doctor ID
  private String userId;   // Foreign key: User ID
  private LocalDateTime consultationStart;
  private LocalDateTime consultationEnd;
  private int rating;

  public int getConsultationId() {
    return consultationId;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public String getUserId() {
    return userId;
  }

  public LocalDateTime getConsultationStart() {
    return consultationStart;
  }

  public LocalDateTime getConsultationEnd() {
    return consultationEnd;
  }

  public int getRating() {
    return rating;
  }

  public void setConsultationId(int consultationId) {
    this.consultationId = consultationId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setConsultationStart(LocalDateTime consultationStart) {
    this.consultationStart = consultationStart;
  }

  public void setConsultationEnd(LocalDateTime consultationEnd) {
    this.consultationEnd = consultationEnd;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }
  // getters and setters
  @Override
  public String toString() {
    return "Consultation{" +
        "consultationId=" + consultationId +
        ", doctorId='" + doctorId + '\'' +
        ", userId='" + userId + '\'' +
        ", consultationStart=" + consultationStart +
        ", consultationEnd=" + consultationEnd +
        ", rating=" + rating +
        '}';
  }
}
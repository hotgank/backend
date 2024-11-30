package org.example.backend.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class DoctorGetReportDTO {
  private int reportId;
  private String childId; // Foreign key: Child ID
  private LocalDateTime createdAt;
  private String reportType;
  private String state;
  private String result;
  private String analyse;
  private String comment;
  private String doctorId;
  private String url;
  private String name;
  private String gender;
  private Date birthdate;
  private int height;
  private int weight;

  // Getters and setters
  public int getReportId() {
    return reportId;
  }

  public void setReportId(int reportId) {
    this.reportId = reportId;
  }

  public String getChildId() {
    return childId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getReportType() {
    return reportType;
  }

  public void setReportType(String reportType) {
    this.reportType = reportType;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getAnalyse() {
    return analyse;
  }

  public void setAnalyse(String analyse) {
    this.analyse = analyse;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }
}

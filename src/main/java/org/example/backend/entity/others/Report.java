package org.example.backend.entity.others;

import java.time.LocalDateTime;

public class Report {
  private int reportId;
  private String childId; // Foreign key: Child ID
  private LocalDateTime createdAt;
  private String reportType;
  private String state;
  private String result;
  private String analyse;
  private String comment;
  private String doctorId;
  private String url; // Image URL

  public int getReportId() {
    return reportId;
  }

  public String getChildId() {
    return childId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public String getReportType() {
    return reportType;
  }

  public String getResult() {
    return result;
  }

  public String getAnalyse() {
    return analyse;
  }

  public String getComment() {
    return comment;
  }

  public String getUrl() {
    return url;
  }

  public void setReportId(int reportId) {
    this.reportId = reportId;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setReportType(String reportType) {
    this.reportType = reportType;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public void setAnalyse(String analyse) {
    this.analyse = analyse;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  // getters and setters

  public String getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  @Override
  public String toString() {
    return "Report{" +
        "reportId=" + reportId +
        ", childId='" + childId + '\'' +
        ", createdAt=" + createdAt +
        ", reportType='" + reportType + '\'' +
        ", result='" + result + '\'' +
        ", analyse='" + analyse + '\'' +
        ", comment='" + comment + '\'' +
        ", doctorId='" + doctorId + '\'' +
        ", url='" + url + '\'' +
        '}';
  }

  public String getState() {
	return state;
}
  public void setState(String state) {
    this.state = state;
  }
}
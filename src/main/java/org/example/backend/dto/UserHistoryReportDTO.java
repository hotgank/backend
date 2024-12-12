package org.example.backend.dto;

import java.time.LocalDateTime;

public class UserHistoryReportDTO {
  private int id;
  private String childName;
  private String reportType;
  private LocalDateTime createdAt;
  private String state;
  private String result;
  private String analyse;
  private String comment;
  private String url; // Image URL
  private String allowState;

  public String getAllowState() {
    return allowState;
  }

  public void setAllowState(String allowState) {
    this.allowState = allowState;
  }
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getChildName() {
    return childName;
  }

  public void setChildName(String childName) {
    this.childName = childName;
  }

  public String getReportType() {
    return reportType;
  }

  public void setReportType(String reportType) {
    this.reportType = reportType;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // 重写 toString 方法
  @Override
  public String toString() {
    return "UserHistoryReportDTO{"
        + "id="
        + id
        + ", childName='"
        + childName
        + '\''
        + ", reportType='"
        + reportType
        + '\''
        + ", createdAt="
        + createdAt
        + ", state='"
        + state
        + '\''
        + ", result='"
        + result
        + '\''
        + ", analyse='"
        + analyse
        + '\''
        + ", comment='"
        + comment
        + '\''
        + ", url='"
        + url
        + '\''
        + '}';
  }
}

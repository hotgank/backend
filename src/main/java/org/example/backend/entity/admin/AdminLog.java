package org.example.backend.entity.admin;

import java.time.LocalDateTime;

public class AdminLog {
  private int logId;
  private String adminId;  // Foreign key: Admin ID
  private String actionType;
  private String details;
  private LocalDateTime timestamp;

  public int getLogId() {
    return logId;
  }

  public String getAdminId() {
    return adminId;
  }

  public String getActionType() {
    return actionType;
  }

  public String getDetails() {
    return details;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setLogId(int logId) {
    this.logId = logId;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }

  public void setActionType(String actionType) {
    this.actionType = actionType;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
  // getters and setters
}
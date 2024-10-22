package org.example.backend.Entity.Admin;

import java.time.LocalDateTime;

public class Admin {
  private String adminId;
  private String adminType;  // enum('super', 'first', 'second')
  private String supervisorId;
  private String unitName;
  private String username;
  private String password;
  private String email;
  private String phone;
  private String avatarUrl;
  private LocalDateTime registrationDate;
  private LocalDateTime lastLogin;
  private String status; // enum ('active', 'disabled')

  public String getAdminId() {
    return adminId;
  }

  public String getAdminType() {
    return adminType;
  }

  public String getSupervisorId() {
    return supervisorId;
  }

  public String getUnitName() {
    return unitName;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public String getStatus() {
    return status;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }

  public void setAdminType(String adminType) {
    this.adminType = adminType;
  }

  public void setSupervisorId(String supervisorId) {
    this.supervisorId = supervisorId;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }

  public void setLastLogin(LocalDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }

  public void setStatus(String status) {
    this.status = status;
  }
  // getters and setters
}

//Test
//Test2
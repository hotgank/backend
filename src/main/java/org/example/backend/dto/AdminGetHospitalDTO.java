package org.example.backend.dto;

public class AdminGetHospitalDTO {
  private String hospitalName;
  private String address;
  private String adminId;
  private String adminUsername;

  // getter and setter methods
  public String getHospitalName() {
    return hospitalName;
  }
  public void setHospitalName(String hospitalName) {
    this.hospitalName = hospitalName;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getAdminId() {
    return adminId;
  }
  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }
  public String getAdminUsername() {
    return adminUsername;
  }
  public void setAdminUsername(String adminUsername) {
    this.adminUsername = adminUsername;
  }
}

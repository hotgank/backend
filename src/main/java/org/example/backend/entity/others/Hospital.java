package org.example.backend.entity.others;

public class Hospital {
  private String hospitalName;
  private String address;
  private String adminId;

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

  @Override
  public String toString() {
    return "Hospital{"
        + "hospitalName='"
        + hospitalName
        + '\''
        + ", address='"
        + address
        + '\''
        + ", adminId='"
        + adminId
        + '\''
        + '}';
  }
}

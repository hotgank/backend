package org.example.backend.entity.others;


import com.baomidou.mybatisplus.annotation.TableId;

public class Unit {
  @TableId
  private String unitName;
  private String address;
  private String adminId; // Foreign key: Admin ID
  private String unitType;


  public String getUnitName() {
    return unitName;
  }

  public String getAddress() {
    return address;
  }

  public String getAdminId() {
    return adminId;
  }

  public String getUnitType() {
    return unitType;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setAdminId(String adminId) {
    this.adminId = adminId;
  }

  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }
}
package org.example.backend.Entity.Others;

import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;

public class Statistics {
  @TableId
  private Integer statId;
  private String statType;
  private Integer value;
  private Date recordDate;

  public Integer getStatId() {
    return statId;
  }

  public String getStatType() {
    return statType;
  }

  public Integer getValue() {
    return value;
  }

  public Date getRecordDate() {
    return recordDate;
  }

  public void setStatId(Integer statId) {
    this.statId = statId;
  }

  public void setStatType(String statType) {
    this.statType = statType;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public void setRecordDate(Date recordDate) {
    this.recordDate = recordDate;
  }

  // Getters and Setters

}
package org.example.backend.entity.others;

public class DetectionAPI {
  private String apiId;
  private String apiType;
  private int number;
  private String state;

  // getter and setter methods
  public String getApiId() {
    return apiId;
  }
  public void setApiId(String apiId) {
    this.apiId = apiId;
  }
  public String getApiType() {
    return apiType;
  }
  public void setApiType(String apiType) {
    this.apiType = apiType;
  }
  public int getNumber() {
    return number;
  }
  public void setNumber(int number) {
    this.number = number;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
}

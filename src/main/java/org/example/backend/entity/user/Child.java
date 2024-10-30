package org.example.backend.entity.user;

import java.util.Date;

// Child.java
public class Child {
  private String childId;
  private String name;
  private String school;
  private String gender;
  private Date birthdate;
  private int height;
  private int weight;

  public String getChildId() {
    return childId;
  }

  public String getName() {
    return name;
  }

  public String getSchool() {
    return school;
  }

  public String getGender() {
    return gender;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public int getHeight() {
    return height;
  }

  public int getWeight() {
    return weight;
  }

  public void setChildId(String childId) {
    this.childId = childId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }
  // getters and setters

  @Override
  public String toString() {
    return "Child{" +
        "child_id='" + childId + '\'' +
        ", name='" + name + '\'' +
        ", school='" + school + '\'' +
        ", gender='" + gender + '\'' +
        ", birthdate=" + birthdate +
        ", height=" + height +
        ", weight=" + weight +
        '}';
  }
}
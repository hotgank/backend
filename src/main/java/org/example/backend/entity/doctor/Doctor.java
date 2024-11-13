package org.example.backend.entity.doctor;

import java.sql.Date;
import java.time.LocalDateTime;

public class Doctor {
  private String doctorId;
  private String name;
  private String password;
  private String phone;
  private String email;
  private Date birthdate;
  private String gender;
  private String position;
  private String workplace;
  private String qualification;
  private String experience;
  private float rating;
  private String avatarUrl;
  private LocalDateTime registrationDate;
  private LocalDateTime lastLogin;
  private String status; // enum ('active', 'disabled')

  public String getDoctorId() {
    return doctorId;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public Date getbirthdate() {
    return birthdate;
  }

  public String getGender() {
    return gender;
  }

  public String getPosition() {
    return position;
  }

  public String getWorkplace() {
    return workplace;
  }

  public String getQualification() {
    return qualification;
  }

  public String getExperience() {
    return experience;
  }

  public float getRating() {
    return rating;
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

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setbirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public void setWorkplace(String workplace) {
    this.workplace = workplace;
  }

  public void setQualification(String qualification) {
    this.qualification = qualification;
  }

  public void setExperience(String experience) {
    this.experience = experience;
  }

  public void setRating(float rating) {
    this.rating = rating;
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

  @Override
  public String toString() {
    return "{" +
        "doctorId='" + doctorId + '\'' +
        ", name='" + name + '\'' +
        ", password='" + password + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", birthdate=" + (birthdate != null ? birthdate.toString() : "null") +
        ", gender='" + gender + '\'' +
        ", position='" + position + '\'' +
        ", workplace='" + workplace + '\'' +
        ", qualification='" + qualification + '\'' +
        ", experience='" + experience + '\'' +
        ", rating=" + rating +
        ", avatarUrl='" + avatarUrl + '\'' +
        ", registrationDate=" + (registrationDate != null ? registrationDate.toString() : "null") +
        ", lastLogin=" + (lastLogin != null ? lastLogin.toString() : "null") +
        ", status='" + status + '\'' +
        '}';
  }

  public String toJson() {
    return "{"
        + "\"doctorId\":\"" + doctorId + "\","
        + "\"name\":\"" + name + "\","
        + "\"phone\":\"" + phone + "\","
        + "\"email\":\"" + email + "\","
        + "\"birthdate\":" + (birthdate != null ? "\"" + birthdate.toString() + "\"" : "null") + ","
        + "\"gender\":\"" + gender + "\","
        + "\"position\":\"" + position + "\","
        + "\"workplace\":\"" + workplace + "\","
        + "\"qualification\":\"" + qualification + "\","
        + "\"experience\":\"" + experience + "\","
        + "\"rating\":" + rating + ","
        + "\"avatarUrl\":\"" + avatarUrl + "\","
        + "\"registrationDate\":" + (registrationDate != null ? "\"" + registrationDate.toString() + "\"" : "null") + ","
        + "\"lastLogin\":" + (lastLogin != null ? "\"" + lastLogin.toString() + "\"" : "null") + ","
        + "\"status\":\"" + status + "\""
        + "}";
  }


}
package org.example.backend.dto;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UserGetDoctorDTO {

  private String doctorId;
  private String name;
  private String username;
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
  private String status; // enum ('active', 'disabled')
  private String situation; // 0 or 1

  // getter and setter
  public String getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getWorkplace() {
    return workplace;
  }

  public void setWorkplace(String workplace) {
    this.workplace = workplace;
  }

  public String getQualification() {
    return qualification;
  }

  public void setQualification(String qualification) {
    this.qualification = qualification;
  }

  public String getExperience() {
    return experience;
  }

  public void setExperience(String experience) {
    this.experience = experience;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSituation() {
    return situation;
  }

  public void setSituation(String situation) {
    this.situation = situation;
  }

  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedBirthdate =
        birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
    return "UserGetDoctorDTO{"
        + "doctorId='"
        + doctorId
        + '\''
        + ", name='"
        + name
        + '\''
        + ", username='"
        + username
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", email='"
        + email
        + '\''
        + ", birthdate="
        + formattedBirthdate
        + '\''
        + ", gender='"
        + gender
        + '\''
        + ", position='"
        + position
        + '\''
        + ", workplace='"
        + workplace
        + '\''
        + ", qualification='"
        + qualification
        + '\''
        + ", experience='"
        + experience
        + '\''
        + ", rating="
        + rating
        + ", avatarUrl='"
        + avatarUrl
        + '\''
        + ", status='"
        + status
        + '\''
        + ", situation="
        + situation
        + '}';
  }
}

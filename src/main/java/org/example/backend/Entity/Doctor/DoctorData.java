package org.example.backend.Entity.Doctor;

import java.util.Date;

public class DoctorData {
  private String doctorId;  // Foreign key: Doctor ID
  private Date date;
  private float rating;
  private int patients;
  private int consultations;
  private int papers;

  public String getDoctorId() {
    return doctorId;
  }

  public Date getDate() {
    return date;
  }

  public float getRating() {
    return rating;
  }

  public int getPatients() {
    return patients;
  }

  public int getConsultations() {
    return consultations;
  }

  public int getPapers() {
    return papers;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public void setPatients(int patients) {
    this.patients = patients;
  }

  public void setConsultations(int consultations) {
    this.consultations = consultations;
  }

  public void setPapers(int papers) {
    this.papers = papers;
  }
  // getters and setters
}
package org.example.backend.dto;

import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.others.Message;

public class LastMessage {
  Doctor doctor;
  Message message;

  public LastMessage(Doctor doctor, Message message) {
    this.doctor = doctor;
    this.message = message;
  }
  public Doctor getDoctor() {
    return doctor;
  }
  public Message getMessage() {
    return message;
  }
  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
  }
  public void setMessage(Message message) {
    this.message = message;
  }
}

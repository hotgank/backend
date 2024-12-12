package org.example.backend.dto;

import org.example.backend.entity.user.User;

public class MessageHandlePlus {
  private User user;
  private int relationId;
  private int doctorUnread;
  private int userUnread;

  public MessageHandlePlus(User user, int relationId, int doctorUnread, int userUnread) {
    this.user = user;
    this.relationId = relationId;
    this.doctorUnread = doctorUnread;
    this.userUnread = userUnread;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getRelationId() {
    return relationId;
  }

  public void setRelationId(int relationId) {
    this.relationId = relationId;
  }

  public int getDoctorUnread() {
    return doctorUnread;
  }

  public void setDoctorUnread(int doctorUnread) {
    this.doctorUnread = doctorUnread;
  }

  public int getUserUnread() {
    return userUnread;
  }

  public void setUserUnread(int userUnread) {
    this.userUnread = userUnread;
  }

  @Override
  public String toString() {
    return "MessageHandlePlus [user=" + user + ", relationId=" + relationId +
        ", doctorUnread=" + doctorUnread + ", userUnread=" + userUnread + "]";
  }
}

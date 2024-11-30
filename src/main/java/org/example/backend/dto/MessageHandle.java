package org.example.backend.dto;

import org.example.backend.entity.user.User;

public class MessageHandle {
  private User user;
  int relationId;

  public MessageHandle(User user, int relationId) {
    this.user = user;
    this.relationId = relationId;
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

  @Override
  public String toString() {
    return "MessageHandle [user=" + user + ", relationId=" + relationId + "]";
  }
}

package org.example.backend.entity.user;

public class WeChatUser {
  private String userId; // Foreign key: User ID
  private String openid;
  private String sessionKey;
  private String unionid;

  public String getUserId() {
    return userId;
  }

  public String getOpenid() {
    return openid;
  }

  public String getSessionKey() {
    return sessionKey;
  }

  public String getUnionid() {
    return unionid;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }

  public void setUnionid(String unionid) {
    this.unionid = unionid;
  }
  // getters and setters
}
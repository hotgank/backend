package org.example.backend.dto;

public class SendMessageRequest {
  private Integer relationId; // 关系ID
  private String senderType; // 发送者类型 (doctor 或 user)
  private String messageText; // 消息内容
  private String messageType; // 消息类型 (text, image, video, file)
  private String url; // 文件路径或URL

  // Getters
  public Integer getRelationId() {
    return relationId;
  }

  public String getSenderType() {
    return senderType;
  }

  public String getMessageText() {
    return messageText;
  }

  public String getMessageType() {
    return messageType;
  }

  public String getUrl() {
    return url;
  }

  // Setters
  public void setRelationId(Integer relationId) {
    this.relationId = relationId;
  }

  public void setSenderType(String senderType) {
    this.senderType = senderType;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // toString
  @Override
  public String toString() {
    return "SendMessageRequest{"
        + "relationId="
        + relationId
        + ", senderType='"
        + senderType
        + '\''
        + ", messageText='"
        + messageText
        + '\''
        + ", messageType='"
        + messageType
        + '\''
        + ", url='"
        + url
        + '\''
        + '}';
  }
}

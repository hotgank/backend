package org.example.backend.entity.others;

import java.time.LocalDateTime;

public class Message {
  private Integer relationId; // 医生-用户关系的唯一标识
  private Integer messageSeq; // 消息序号
  private String senderType; // 发送者类型（doctor 或 user）
  private String messageText; // 消息内容
  private LocalDateTime timestamp; // 消息时间戳
  private String messageType; // 消息类型（text、image、video、file 等）
  private String url; // 消息附件的 URL

  public Message() {}

  // Getter for relationId
  public Integer getRelationId() {
    return relationId;
  }

  // Setter for relationId
  public void setRelationId(Integer relationId) {
    this.relationId = relationId;
  }

  // Getter for messageSeq
  public Integer getMessageSeq() {
    return messageSeq;
  }

  // Setter for messageSeq
  public void setMessageSeq(Integer messageSeq) {
    this.messageSeq = messageSeq;
  }

  // Getter for senderType
  public String getSenderType() {
    return senderType;
  }

  // Setter for senderType
  public void setSenderType(String senderType) {
    this.senderType = senderType;
  }

  // Getter for messageText
  public String getMessageText() {
    return messageText;
  }

  // Setter for messageText
  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  // Getter for timestamp
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  // Setter for timestamp
  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  // Getter for messageType
  public String getMessageType() {
    return messageType;
  }

  // Setter for messageType
  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  // Getter for url
  public String getUrl() {
    return url;
  }

  // Setter for url
  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "Message{"
        + "relationId="
        + relationId
        + ", messageSeq="
        + messageSeq
        + ", senderType='"
        + senderType
        + '\''
        + ", messageText='"
        + messageText
        + '\''
        + ", timestamp="
        + timestamp
        + ", messageType='"
        + messageType
        + '\''
        + ", url='"
        + url
        + '\''
        + '}';
  }
}

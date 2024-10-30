package org.example.backend.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

public class Message {
  @TableId
  private int messageId;
  private int consultationId;  // Foreign key: Consultation ID
  private String senderType;   // enum ('doctor', 'user')
  private String messageText;
  private LocalDateTime timestamp;
  private String messageType;  // enum ('text', 'image', 'video', 'file')
  private String url;          // Image or file URL

  public int getMessageId() {
    return messageId;
  }

  public int getConsultationId() {
    return consultationId;
  }

  public String getSenderType() {
    return senderType;
  }

  public String getMessageText() {
    return messageText;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getMessageType() {
    return messageType;
  }

  public String getUrl() {
    return url;
  }

  public void setMessageId(int messageId) {
    this.messageId = messageId;
  }

  public void setConsultationId(int consultationId) {
    this.consultationId = consultationId;
  }

  public void setSenderType(String senderType) {
    this.senderType = senderType;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  // getters and setters
}
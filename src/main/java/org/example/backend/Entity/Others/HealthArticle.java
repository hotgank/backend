package org.example.backend.Entity.Others;

import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;

public class HealthArticle {
  @TableId
  private Integer articleId;
  private String doctorId;
  private String title;
  private String content;
  private Date publishDate;
  private String type;
  private String status;


  public Integer getArticleId() {
    return articleId;
  }

  public String getDoctorId() {
    return doctorId;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public Date getPublishDate() {
    return publishDate;
  }

  public String getType() {
    return type;
  }

  public String getStatus() {
    return status;
  }

  public void setArticleId(Integer articleId) {
    this.articleId = articleId;
  }

  public void setDoctorId(String doctorId) {
    this.doctorId = doctorId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setPublishDate(Date publishDate) {
    this.publishDate = publishDate;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
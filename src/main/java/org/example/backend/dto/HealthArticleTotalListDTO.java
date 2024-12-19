package org.example.backend.dto;

import java.util.Date;

public class HealthArticleTotalListDTO {
    private Integer articleId;
    private String name;
    private String title;
    private Date publishDate;
    private String type;
    private String status;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HealthArticleListDTO{" +
                "articleId=" + articleId +
                ", doctorName='" + name + '\'' +
                ", title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

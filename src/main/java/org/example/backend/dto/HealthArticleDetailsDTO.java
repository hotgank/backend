package org.example.backend.dto;

import java.util.Date;

public class HealthArticleDetailsDTO {
    private Integer articleId;
    private String title;
    private String content;
    private Date publishDate;
    private String type;
    private String status;
    private String name;
    private String username;
    private String gender;
    private String position;
    private String workplace;
    private String qualification;
    private String experience;
    private String avatarUrl;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "HealthArticleDetailsDTO{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishDate=" + publishDate +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", doctorName='" + name + '\'' +
                ", username='" + username + '\'' +
                ",gender='" + gender + '\'' +
                ", position='" + position + '\'' +
                ", workplace='" + workplace + '\'' +
                ", qualification='" + qualification + '\'' +
                ", experience='" + experience + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}


package org.example.backend.entity.admin;

import java.time.LocalDateTime;

public class LicenseCheck {
    private String auditId;
    private String doctorId;
    private String adminId;
    private String status;
    private LocalDateTime createdAt;
    private String url;
    private LocalDateTime updatedAt;
    private String comment;

    public String getAuditId() {
        return auditId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "LicenseCheck{" +
                "auditId='" + auditId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", adminId='" + adminId + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", url='" + url + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}

package org.example.backend.dto;

public class AdminGetDoctorLicenseDTO {
    private String auditId;
    private String doctorId;
    private String name;
    private String gender;
    private String position;
    private String workplace;
    private String url;

    public String getAuditId() {
        return auditId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPosition() {
        return position;
    }

    public String getWorkplace() {
        return workplace;
    }

    public String getUrl() {
        return url;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AdminGetDoctorLicenseDTO{" +
                "auditId='" + auditId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", position='" + position + '\'' +
                ", workplace='" + workplace + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

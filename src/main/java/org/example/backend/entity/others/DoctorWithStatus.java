package org.example.backend.entity.others;


import java.util.Date;
import org.example.backend.entity.doctor.Doctor;

public class DoctorWithStatus {
    private Doctor doctor;
    private String relationStatus; // 新增属性

    // Getter 和 Setter 方法
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public String getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus;
    }
}


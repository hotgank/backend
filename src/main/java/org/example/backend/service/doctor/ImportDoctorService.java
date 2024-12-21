package org.example.backend.service.doctor;

import org.example.backend.entity.doctor.Doctor;

import java.util.List;

public interface ImportDoctorService {
    boolean insertAllDoctors(List<Doctor> doctors);
}

package org.example.backend.controller.doctor;

import org.example.backend.entity.doctor.Doctor;
import org.example.backend.Service.doctor.DoctorService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

  @Autowired
  private DoctorService doctorService;

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAll() {
    // 调用服务层来查询所有医生信息
    List<Doctor> doctors = doctorService.getAll();
    return ResponseEntity.ok(doctors.toString());
  }

  @PostMapping("/selectById")
  public ResponseEntity<String> selectById(@RequestBody String doctorIdJson) {
    String doctorId = JsonParser.parseJsonString(doctorIdJson, "doctorId");
    // 调用服务层来根据doctorId查询医生信息
    Doctor selectedDoctor = doctorService.getById(doctorId);

    if (selectedDoctor != null) {
      return ResponseEntity.ok(selectedDoctor.toString());
    } else {
      return ResponseEntity.status(500).body("Failed to find doctor information");
    }
  }

  @PostMapping("/add")
  public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) {
    // 调用服务层来添加医生信息到数据库
    boolean success = doctorService.createDoctor(doctor);

    if (success) {
      return ResponseEntity.ok("Doctor information added successfully, doctorId: " + doctor.getDoctorId());
    } else {
      return ResponseEntity.status(500).body("Failed to add doctor information");
    }
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateDoctor(@RequestBody Doctor doctor) {
    // 调用服务层来更新医生信息
    boolean success = doctorService.updateDoctor(doctor);

    if (success) {
      return ResponseEntity.ok("Doctor information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update doctor information");
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<String> deleteDoctor(@RequestBody String doctorIdJson) {
    String doctorId = JsonParser.parseJsonString(doctorIdJson, "doctorId");
    // 调用服务层来删除医生信息
    boolean success = doctorService.deleteDoctor(doctorId);

    if (success) {
      return ResponseEntity.ok("Doctor information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to delete doctor information");
    }
  }
}

package org.example.backend.controller.doctor;


import org.example.backend.entity.doctor.Doctor;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

  @Autowired
  private DoctorService doctorService;

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAll() {

    // 调用服务层来查询所有医生信息
    String result = doctorService.selectAll().toString();

    return ResponseEntity.ok(result);
  }

  @PostMapping("/selectById")
  public ResponseEntity<String> selectById(@RequestBody String doctorIdJson) {
    String doctorId = JsonParser.parseJsonString(doctorIdJson, "doctorId");
    // 调用服务层来根据doctorId查询医生信息
    Doctor selectedDoctor = doctorService.selectById(doctorId);

    if (selectedDoctor != null) {
      return ResponseEntity.ok(selectedDoctor.toString());
    } else {
      return ResponseEntity.status(500).body("Failed to add doctor information");
    }
  }

  // 处理添加医生信息的请求
  @PostMapping("/add")
  public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) {

    // 调用服务层来添加医生信息到数据库
    String result = doctorService.insert(doctor);

    if (result != null) {
      return ResponseEntity.ok("doctor information added successfully, doctorId: " + result);
    } else {
      return ResponseEntity.status(500).body("Failed to add doctor information");
    }
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateDoctor(@RequestBody Doctor doctor) {

    // 调用服务层来更新医生信息
    boolean success = doctorService.update(doctor);

    if (success) {
      return ResponseEntity.ok("doctor information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update doctor information");
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<String> deleteDoctor(@RequestBody String doctorIdJson) {
    String doctorId = JsonParser.parseJsonString(doctorIdJson, "doctorId");
    // 调用服务层来删除医生信息
    boolean success = doctorService.delete(doctorId);

    if (success) {
      return ResponseEntity.ok("doctor information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to delete doctor information");
    }
  }
}
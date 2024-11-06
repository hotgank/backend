package org.example.backend.controller.doctor;


import org.example.backend.entity.doctor.Doctor;
import org.example.backend.Service.doctor.DoctorService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<String> deleteAccount(@RequestBody String doctorJson) {
    String doctorId = JsonParser.parseJsonString(doctorJson, "doctorId");
    String password = JsonParser.parseJsonString(doctorJson, "password");

    if (!doctorService.validatePassword(doctorId, password)){
      return ResponseEntity.status(400).body("Failed to find doctor information");
    }
    // 调用服务层来删除医生信息
    boolean success = doctorService.delete(doctorId);

    if (success) {
      return ResponseEntity.ok("doctor information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to delete doctor information");
    }
  }

  @PostMapping("/updatePassword")
  public ResponseEntity<String> updatePassword(@RequestBody String doctorJson){
    String doctorId = JsonParser.parseJsonString(doctorJson, "doctorId");
    String oldPassword = JsonParser.parseJsonString(doctorJson, "oldPassword");
    if(!doctorService.validatePassword(doctorId, oldPassword)){
      return ResponseEntity.status(400).body("Failed to find doctor information");
    }
    String newPassword1 = JsonParser.parseJsonString(doctorJson, "newPassword1");
    String newPassword2 = JsonParser.parseJsonString(doctorJson, "newPassword2");
    if (newPassword1 != null && !newPassword1.equals(newPassword2)) {
      return ResponseEntity.status(400).body("Passwords do not match");
    }
    if(doctorService.updatePassword(doctorId, newPassword1)){
      return ResponseEntity.ok("Password updated successfully");
    }else{
      return ResponseEntity.status(500).body("Failed to update password");
    }
  }

}
package org.example.backend.controller.doctor;


import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.util.ExcelReader;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

  @Autowired
  private DoctorService doctorService;

  @Autowired
  private JsonParser jsonParser;

  @Autowired
  private ExcelReader excelReader;

  @PostMapping("/selectAll")
  public ResponseEntity<String> selectAll() {

    // 调用服务层来查询所有医生信息
    List<Doctor> result = doctorService.selectAll();

    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(result));
  }

  @GetMapping("/selectById")
  public ResponseEntity<String> selectById(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("doctorId");
    // 调用服务层来根据doctorId查询医生信息
    Doctor selectedDoctor = doctorService.selectById(doctorId);

    if (selectedDoctor != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntity(selectedDoctor));
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
      return ResponseEntity.ok("{\\\"doctorId\\\": \\\"\" + result + \"\\\"}");
    } else {
      return ResponseEntity.status(500).body("Failed to add doctor information");
    }
  }

  @PostMapping("/insertAll")
  public ResponseEntity<String> insertAllDoctors(@RequestBody String urlJson) {
    String url = jsonParser.parseJsonString(urlJson, "url");
    List<Doctor> doctors = excelReader.readExcel(url, Doctor.class);
    // 调用服务层来添加医生信息到数据库
    boolean success = doctorService.insertAllDoctors(doctors);
    if (success) {
      return ResponseEntity.ok("doctor information added successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to add doctor information");
    }
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateDoctor(@RequestBody Doctor doctor, HttpServletRequest request) {
    doctor.setDoctorId((String) request.getAttribute("doctorId"));
    // 调用服务层来更新医生信息
    boolean success = doctorService.update(doctor);

    if (success) {
      return ResponseEntity.ok("doctor information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update doctor information");
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<String> deleteAccount(@RequestBody String doctorJson, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("doctorId");
    String password = jsonParser.parseJsonString(doctorJson, "password");

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

  @GetMapping("/updatePassword")
  public ResponseEntity<String> updatePassword(@RequestBody String doctorJson, HttpServletRequest request){
    String doctorId = (String) request.getAttribute("doctorId");
    String oldPassword = jsonParser.parseJsonString(doctorJson, "oldPassword");
    if(!doctorService.validatePassword(doctorId, oldPassword)){
      return ResponseEntity.status(400).body("Failed to find doctor information");
    }
    String newPassword1 = jsonParser.parseJsonString(doctorJson, "newPassword1");
    String newPassword2 = jsonParser.parseJsonString(doctorJson, "newPassword2");
    if (newPassword1 != null && !newPassword1.equals(newPassword2)) {
      return ResponseEntity.status(400).body("Passwords do not match");
    }
    if(doctorService.updatePassword(doctorId, newPassword1)){
      return ResponseEntity.ok("Password updated successfully");
    }else{
      return ResponseEntity.status(500).body("Failed to update password");
    }
  }

  @GetMapping("/information")
  public ResponseEntity<String> getInformation(HttpServletRequest request) {
    try {
      //从请求中获取用户ID
      String userId = (String) request.getAttribute("userId");

      return ResponseEntity.ok("{\"Id\":\""+userId+"\"}");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed");
    }
  }

}
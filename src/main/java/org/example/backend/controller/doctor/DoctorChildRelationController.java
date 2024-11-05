package org.example.backend.controller.doctor;

import org.example.backend.entity.doctor.DoctorChildRelation;
import org.example.backend.service.doctor.DoctorChildRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/doctor/relation")
public class DoctorChildRelationController {
  @Autowired
  DoctorChildRelationService doctorChildRelationService;

  @PostMapping("/selectMyPatients")
  public ResponseEntity<String> selectMyPatients(@RequestBody DoctorChildRelation relation) {
    // 调用服务层来查询管理员信息
    return ResponseEntity.ok(doctorChildRelationService.selectMyPatients(relation).toString());
  }

  @PostMapping("/add")
  public ResponseEntity<String> addDoctorChildRelation(@RequestBody DoctorChildRelation relation) {
    // 调用服务层来添加管理员信息到数据库
    boolean success = doctorChildRelationService.createDoctorChildRelation(relation);

    if (success) {
      return ResponseEntity.ok("Successfully bound doctor with child");
    } else {
      return ResponseEntity.status(500).body("Failed to bound doctor with child");
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<String> deleteDoctorChildRelation(@RequestBody DoctorChildRelation relation) {
    // 调用服务层来添加管理员信息到数据库
    boolean success = doctorChildRelationService.deleteDoctorChildRelation(relation);

    if (success) {
      return ResponseEntity.ok("Successfully deleted doctor child relation");
    } else {
      return ResponseEntity.status(500).body("Failed to delete doctor child relation");
    }
  }
}

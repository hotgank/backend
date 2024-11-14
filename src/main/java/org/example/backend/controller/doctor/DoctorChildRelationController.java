package org.example.backend.controller.doctor;

import java.util.List;
import org.example.backend.entity.doctor.DoctorChildRelation;
import org.example.backend.entity.user.Child;
import org.example.backend.service.doctor.DoctorChildRelationService;
import org.example.backend.util.JsonParser;
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

  @Autowired
  private JsonParser jsonParser;

  @PostMapping("/selectMyPatients")
  public ResponseEntity<String> selectMyPatients(@RequestBody String doctorIdJson) {
    String doctorId = jsonParser.parseJsonString(doctorIdJson, "doctorId");
    // 调用服务层来查询患者信息
    List<Child> relations = doctorChildRelationService.selectMyPatients(doctorId, "approved");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relations));
  }

  @PostMapping("/selectPendingPatients")
  public ResponseEntity<String> selectPendingPatients(@RequestBody String doctorIdJson) {
    // 调用服务层来查询待绑定患者信息
    String doctorId = jsonParser.parseJsonString(doctorIdJson, "doctorId");
    // 调用服务层来查询患者信息
    List<Child> relations = doctorChildRelationService.selectMyPatients(doctorId, "pending");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relations));
  }

  @PostMapping("/add")
  public ResponseEntity<String> addDoctorChildRelation(@RequestBody DoctorChildRelation relation) {
    // 调用服务层来添加医患信息到数据库
    int result = doctorChildRelationService.createDoctorChildRelation(relation);

    if (result > 0) {
      return ResponseEntity.ok("Successfully bound doctor with child");
    } else {
      return ResponseEntity.status(500).body("Failed to bound doctor with child");
    }
  }

  @PostMapping("/approve")
  public ResponseEntity<String> approveDoctorChildRelation(@RequestBody DoctorChildRelation relation) {
    relation.setRelationStatus("approved");
    // 调用服务层来通过医患信息到数据库
    boolean success = doctorChildRelationService.updateDoctorChildRelation(relation);

    if (success) {
      return ResponseEntity.ok("Successfully reviewed doctor child relation");
    } else {
      return ResponseEntity.status(500).body("Failed to review doctor child relation");
    }
  }

  @PostMapping("/reject")
  public ResponseEntity<String> rejectDoctorChildRelation(@RequestBody DoctorChildRelation relation) {
    relation.setRelationStatus("rejected");
    // 调用服务层来拒绝医患信息到数据库
    boolean success = doctorChildRelationService.updateDoctorChildRelation(relation);

    if (success) {
      return ResponseEntity.ok("Successfully reviewed doctor child relation");
    } else {
      return ResponseEntity.status(500).body("Failed to review doctor child relation");
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<String> deleteDoctorChildRelation(@RequestBody DoctorChildRelation relation) {
    // 调用服务层来删除咨询医患信息到数据库
    boolean success = doctorChildRelationService.deleteDoctorChildRelation(relation);

    if (success) {
      return ResponseEntity.ok("Successfully deleted doctor child relation");
    } else {
      return ResponseEntity.status(500).body("Failed to delete doctor child relation");
    }
  }
}

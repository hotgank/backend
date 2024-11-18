package org.example.backend.controller.doctor;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.others.DoctorWithStatus;
import org.example.backend.service.doctor.DoctorUserRelationService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.backend.entity.user.User;

@RestController
@RequestMapping("api/doctor/relation")
public class DoctorUserRelationController {
  @Autowired
  DoctorUserRelationService doctorUserRelationService;

  @Autowired
  private JsonParser jsonParser;

  @GetMapping("/selectApplication")
  public ResponseEntity<String> selectApplication(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    // 调用服务层来查询医生信息
    List<DoctorWithStatus>    doctorWithStatuses= doctorUserRelationService.selectPendingDoctors(userId);
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(doctorWithStatuses));
  }

  @GetMapping("/selectMyDoctors")
  public ResponseEntity<String> selectMyDoctors(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    // 调用服务层来查询医生信息
    List<Doctor> doctors = doctorUserRelationService.selectMyDoctors(userId);
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(doctors));
  }
  @GetMapping("/selectMyPatients")
  public ResponseEntity<String> selectMyPatients(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询患者信息
    List<User> relations = doctorUserRelationService.selectMyPatients(doctorId, "approved");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relations));
  }

  @GetMapping("/selectPendingPatients")
  public ResponseEntity<String> selectPendingPatients(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询待绑定患者信息
    List<User> relations = doctorUserRelationService.selectMyPatients(doctorId, "pending");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relations));
  }

  @GetMapping("/selectRecentPatients")
  public ResponseEntity<String> selectRecentPatients(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询待绑定患者信息
    List<User> relations = doctorUserRelationService.selectRecentPatients(doctorId, "approved");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relations));
  }

  @GetMapping("/selectRecentPendingPatients")
  public ResponseEntity<String> selectRecentPendingPatients(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询待绑定患者信息
    List<User> relations = doctorUserRelationService.selectRecentPatients(doctorId, "pending");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relations));
  }

  @PostMapping("/add")
  public ResponseEntity<String> addDoctorUserRelation(@RequestBody DoctorUserRelation relation, HttpServletRequest request) {
    // 调用服务层来添加医患信息到数据库
    relation.setUserId((String) request.getAttribute("userId"));
    DoctorUserRelation doctorUserRelation = doctorUserRelationService.selectDoctorUserRelationByIDs(relation.getDoctorId(), relation.getUserId());
    if(doctorUserRelation != null) {
      if(doctorUserRelation.getRelationStatus().equals("pending")) {
        return ResponseEntity.status(500).body("You had sent a pending invitation already");
      }else if(doctorUserRelation.getRelationStatus().equals("approved")) {
        return ResponseEntity.status(500).body("You have been bound with this doctor already");
      }else {
        return ResponseEntity.status(500).body("You had been rejected already");
      }
    }
    int result = doctorUserRelationService.createDoctorUserRelation(relation);

    if (result > 0) {
      return ResponseEntity.ok("Successfully bound doctor with child");
    } else {
      return ResponseEntity.status(500).body("Failed to bound doctor with child");
    }
  }

  @PostMapping("/approve")
  public ResponseEntity<String> approveDoctorUserRelation(@RequestBody DoctorUserRelation relation) {
    relation.setRelationStatus("approved");
    // 调用服务层来通过医患信息到数据库
    boolean success = doctorUserRelationService.updateDoctorUserRelation(relation);

    if (success) {
      return ResponseEntity.ok("Successfully reviewed doctor child relation");
    } else {
      return ResponseEntity.status(500).body("Failed to review doctor child relation");
    }
  }

  @PostMapping("/reject")
  public ResponseEntity<String> rejectDoctorUserRelation(@RequestBody DoctorUserRelation relation) {
    relation.setRelationStatus("rejected");
    // 调用服务层来拒绝医患信息到数据库
    boolean success = doctorUserRelationService.updateDoctorUserRelation(relation);

    if (success) {
      return ResponseEntity.ok("Successfully reviewed doctor child relation");
    } else {
      return ResponseEntity.status(500).body("Failed to review doctor child relation");
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<String> deleteDoctorUserRelation(@RequestBody DoctorUserRelation relation) {
    // 调用服务层来删除咨询医患信息到数据库
    boolean success = doctorUserRelationService.deleteDoctorUserRelation(relation);

    if (success) {
      return ResponseEntity.ok("Successfully deleted doctor child relation");
    } else {
      return ResponseEntity.status(500).body("Failed to delete doctor child relation");
    }
}

  //获取待审核列表
  @GetMapping("/selectPending")
  public ResponseEntity<String> selectPending(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询待审核信息
    List<DoctorUserRelation> relations = doctorUserRelationService.selectPendingPatients(doctorId, "pending");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relations));
  }
}

package org.example.backend.controller.doctor;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.dto.DoctorGetReportDTO;
import org.example.backend.dto.DoctorGetUserBindingDTO;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.others.DoctorWithStatus;
import org.example.backend.entity.others.Message;
import org.example.backend.entity.others.Report;
import org.example.backend.service.doctor.DoctorUserRelationService;
import org.example.backend.service.others.MessageService;
import org.example.backend.service.others.ReportService;
import org.example.backend.util.JsonParser;
import org.example.backend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.backend.entity.user.User;
import org.example.backend.dto.MessageHandle;
import org.example.backend.dto.MessageHandlePlus;

@RestController
@RequestMapping("api/doctor/relation")
public class DoctorUserRelationController {
  @Autowired DoctorUserRelationService doctorUserRelationService;

  @Autowired private JsonParser jsonParser;

  @Autowired private ReportService reportService;

  @Autowired private MessageService messageService;

  @Autowired private RedisUtil redisUtil;

  @GetMapping("/selectApplication")
  public ResponseEntity<String> selectApplication(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    // 调用服务层来查询医生信息
    List<DoctorWithStatus> doctorWithStatuses =
        doctorUserRelationService.selectPendingDoctors(userId);
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(doctorWithStatuses));
  }

  @GetMapping("/selectMyDoctors")
  public ResponseEntity<String> selectMyDoctors(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    // 调用服务层来查询医生信息
    List<Doctor> doctors = doctorUserRelationService.selectMyDoctors(userId);
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(doctors));
  }

  @GetMapping("/selectMyPatientsAndRelationId")
  public ResponseEntity<String> selectMyPatientsAndRelationId(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询患者信息
    List<User> relations = doctorUserRelationService.selectMyPatients(doctorId, "approved");
    List<MessageHandle> messages = new ArrayList<>();
    for (User user : relations) {
      messages.add(
          new MessageHandle(
              user,
              doctorUserRelationService
                  .selectDoctorUserRelationByIDs(doctorId, user.getUserId())
                  .getRelationId()));
    }

    String json = jsonParser.toJsonFromEntityList(messages);

    json = jsonParser.removeKeyFromJson(json, "password");
    json = jsonParser.removeKeyFromJson(json, "email");
    json = jsonParser.removeKeyFromJson(json, "phone");
    json = jsonParser.removeKeyFromJson(json, "registrationDate");
    json = jsonParser.removeKeyFromJson(json, "lastLogin");
    json = jsonParser.removeKeyFromJson(json, "openid");
    return ResponseEntity.ok(json);
  }

  @GetMapping("/selectMyPatients")
  public ResponseEntity<String> selectMyPatients(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询患者信息
    List<User> relations = doctorUserRelationService.selectMyPatients(doctorId, "approved");
    List<MessageHandle> messages = new ArrayList<>();
    for (User user : relations) {
      messages.add(
          new MessageHandle(
              user,
              doctorUserRelationService
                  .selectDoctorUserRelationByIDs(doctorId, user.getUserId())
                  .getRelationId()));
    }

    String json = jsonParser.toJsonFromEntityList(messages);

    json = jsonParser.removeKeyFromJson(json, "password");
    json = jsonParser.removeKeyFromJson(json, "email");
    json = jsonParser.removeKeyFromJson(json, "phone");
    json = jsonParser.removeKeyFromJson(json, "registrationDate");
    json = jsonParser.removeKeyFromJson(json, "lastLogin");
    json = jsonParser.removeKeyFromJson(json, "openid");
    return ResponseEntity.ok(json);
  }

  @GetMapping("/selectPendingPatients")
  public ResponseEntity<String> selectPendingPatients(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询待绑定患者信息
    List<User> relations = doctorUserRelationService.selectMyPatients(doctorId, "pending");

    String json = jsonParser.toJsonFromEntityList(relations);

    json = jsonParser.removeKeyFromJson(json, "password");
    json = jsonParser.removeKeyFromJson(json, "email");
    json = jsonParser.removeKeyFromJson(json, "phone");
    json = jsonParser.removeKeyFromJson(json, "registrationDate");
    json = jsonParser.removeKeyFromJson(json, "lastLogin");
    json = jsonParser.removeKeyFromJson(json, "openid");
    json = jsonParser.removeKeyFromJson(json, "sessionKey");
    return ResponseEntity.ok(json);
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
  public ResponseEntity<String> addDoctorUserRelation(
      @RequestBody DoctorUserRelation relation, HttpServletRequest request) {
    // 调用服务层来添加医患信息到数据库
    relation.setUserId((String) request.getAttribute("userId"));
    // 根据userId查找用户医生关系，判断状态不为"rejected"的关系是否超过5个，如果大于5，则返回错误
    List<DoctorUserRelation> relations =
        doctorUserRelationService.getRelationsByUserId(relation.getUserId());
    int count = 0;
    for (DoctorUserRelation r : relations) {
      if (r.getRelationStatus().equals("pending")
          || r.getRelationStatus().equals("approved")) {
        count++;
      }
    }
    if (count >= 20) {
      return ResponseEntity.status(500).body("You have too many pending invitations");
    }
    DoctorUserRelation doctorUserRelation =
        doctorUserRelationService.selectDoctorUserRelationByIDs(
            relation.getDoctorId(), relation.getUserId());
    int result = 0;
    if (doctorUserRelation != null) {
      if (doctorUserRelation.getRelationStatus().equals("pending")) {
        return ResponseEntity.status(500).body("You had sent a pending invitation already");
      } else if (doctorUserRelation.getRelationStatus().equals("approved")) {
        return ResponseEntity.status(500).body("You have been bound with this doctor already");
      } else if (doctorUserRelation.getRelationStatus().equals("rejected")||doctorUserRelation.getRelationStatus().equals("removeBinding")){
        doctorUserRelation.setRelationStatus("pending");
        boolean updateResult = doctorUserRelationService.updateDoctorUserRelation(doctorUserRelation);
        if (updateResult) {
          return ResponseEntity.ok("Successfully bound doctor with child");
        } else {
          return ResponseEntity.status(500).body("Failed to bound doctor with child");
        }
      }
    } else {
      result = doctorUserRelationService.createDoctorUserRelation(relation);
    }

    if (result > 0) {
      return ResponseEntity.ok("Successfully bound doctor with child");
    } else {
      return ResponseEntity.status(500).body("Failed to bound doctor with child");
    }
  }

  @PostMapping("/approve")
  public ResponseEntity<String> approveDoctorUserRelation(
      @RequestBody String jsonString, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String userId = jsonParser.parseJsonString(jsonString, "userId");
    DoctorUserRelation relation =
        doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, userId);
    if (relation == null) {
      return ResponseEntity.status(500)
          .body("You have no permission to access this user's reports");
    }
    relation.setRelationStatus("approved");
    // 调用服务层来通过医患信息到数据库

    boolean success = doctorUserRelationService.updateDoctorUserRelation(relation);

    if (success) {
      //医生发送信息
      messageService.sendMessage(relation.getRelationId(), "doctor", "我已通过你的申请，可以开始咨询啦", "text", "");
      //初始化已读消息序列号
      redisUtil.setNoExpireKey(relation.getRelationId() + "_" + "doctor", 0);
      redisUtil.setNoExpireKey(relation.getRelationId() + "_" + "user", 0);
      return ResponseEntity.ok("Successfully reviewed doctor child relation");
    } else {
      return ResponseEntity.status(500).body("Failed to review doctor child relation");
    }
  }

  @PostMapping("/reject")
  public ResponseEntity<String> rejectDoctorUserRelation(
      @RequestBody String jsonString, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String userId = jsonParser.parseJsonString(jsonString, "userId");
    DoctorUserRelation relation =
        doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, userId);
    if (relation == null) {
      return ResponseEntity.status(500)
          .body("You have no permission to access this user's reports");
    }
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

  // 获取待审核列表
  @GetMapping("/selectPending")
  public ResponseEntity<String> selectPending(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询待审核信息
    List<DoctorGetUserBindingDTO> relations =
        doctorUserRelationService.selectPendingPatients(doctorId, "pending");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relations));
  }

  @PostMapping("/selectReports")
  public ResponseEntity<String> selectReports(
      @RequestBody String jsonString, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String userId = jsonParser.parseJsonString(jsonString, "userId");
    DoctorUserRelation relation =
        doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, userId);
    if (relation == null) {
      return ResponseEntity.status(500)
          .body("You have no permission to access this user's reports");
    }
    // 调用服务层来查询待审核信息
    List<DoctorGetReportDTO> reports = reportService.DoctorGetReportByUserId(userId);
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(reports));
  }

  @GetMapping("/doctor/getRelationsByDoctorId")
  public ResponseEntity<String> getRelationsByDoctorId(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String jsonString =
        jsonParser.toJsonFromEntityList(doctorUserRelationService.getRelationsByDoctorId(doctorId));
    jsonString = jsonParser.removeKeyFromJson(jsonString, "doctorId");
    return ResponseEntity.ok(jsonString);
  }

  @GetMapping("/user/getRelationsByUserId")
  public ResponseEntity<String> getRelationsByUserId(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    String jsonString =
        jsonParser.toJsonFromEntityList(doctorUserRelationService.getRelationsByDoctorId(userId));
    jsonString = jsonParser.removeKeyFromJson(jsonString, "userId");
    return ResponseEntity.ok(jsonString);
  }

  // 解除医生用户关系的申请
  @PostMapping("/removeBinding")
  public ResponseEntity<String> removeBinding(
      @RequestBody DoctorUserRelation relation, HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    relation.setUserId(userId);
    // 先查询是否存在该关系且状态为approved
    DoctorUserRelation existRelation =
        doctorUserRelationService.selectDoctorUserRelationByIDs(
            relation.getDoctorId(), relation.getUserId());
    if (existRelation == null || !existRelation.getRelationStatus().equals("approved")) {
      return ResponseEntity.status(500)
          .body("You have no permission to remove this doctor user relation");
    }
    relation.setRelationStatus("removeBinding");
    boolean success = doctorUserRelationService.updateDoctorUserRelation(relation);
    if (success) {
      return ResponseEntity.ok("Successfully removed doctor user relation");
    } else {
      return ResponseEntity.status(500).body("Failed to remove doctor user relation");
    }
  }

  // 同意解除医生用户关系的申请，删除关系
  @PostMapping("/agreeRemoveBinding")
  public ResponseEntity<String> agreeRemoveBinding(
      @RequestBody String jsonString, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String userId = jsonParser.parseJsonString(jsonString, "userId");
    // 先查询是否存在该关系且状态为removeBinding
    DoctorUserRelation existRelation =
        doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, userId);
    if (existRelation == null || !existRelation.getRelationStatus().equals("removeBinding")) {
      return ResponseEntity.status(500)
          .body("You have no permission to agree remove this doctor user relation");
    }
    boolean success = doctorUserRelationService.deleteDoctorUserRelation(existRelation);
    if (success) {
      return ResponseEntity.ok("Successfully removed doctor user relation");
    } else {
      return ResponseEntity.status(500).body("Failed to remove doctor user relation");
    }
  }

  // 拒绝解除医生用户关系的申请
  @PostMapping("/rejectRemoveBinding")
  public ResponseEntity<String> rejectRemoveBinding(
      @RequestBody String jsonString, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String userId = jsonParser.parseJsonString(jsonString, "userId");
    // 先查询是否存在该关系且状态为removeBinding
    DoctorUserRelation existRelation =
        doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, userId);
    if (existRelation == null || !existRelation.getRelationStatus().equals("removeBinding")) {
      return ResponseEntity.status(500)
          .body("You have no permission to reject remove this doctor user relation");
    }
    existRelation.setRelationStatus("approved");
    boolean success = doctorUserRelationService.updateDoctorUserRelation(existRelation);
    if (success) {
      return ResponseEntity.ok("Successfully rejected remove doctor user relation");
    } else {
      return ResponseEntity.status(500).body("Failed to reject remove doctor user relation");
    }
  }

  // 查询待处理的结束咨询申请
  @GetMapping("/selectRemoveBinding")
  public ResponseEntity<String> selectRemoveBinding(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    List<DoctorGetUserBindingDTO> relationList =
        doctorUserRelationService.selectRemoveBindingRelations(doctorId);
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relationList));
  }

  @PostMapping("/selectRelationIdByDoctorId")
  public ResponseEntity<String> selectRelationIdByDoctorId(
      @RequestBody String doctorJson, HttpServletRequest request) {
    String doctorId = jsonParser.parseJsonString(doctorJson, "doctorId");
    String userId = (String) request.getAttribute("userId");
    DoctorUserRelation relation =
        doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, userId);

    if (relation == null) {
      // 处理 relation 为空的情况，返回友好错误信息
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("No relation found for doctorId: " + doctorId + " and userId: " + userId);
    }

    // 如果 relation 不为空，返回其 ID
    return ResponseEntity.ok(jsonParser.toJsonFromEntity(relation.getRelationId()));
  }

  @GetMapping("/selectMyPatientsAndRelationIdAndUnread")
  public ResponseEntity<String> selectMyPatientsAndRelationIdAndUnread(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询患者信息
    List<User> relations = doctorUserRelationService.selectMyPatients(doctorId, "approved");
    List<MessageHandlePlus> messages = new ArrayList<>();
    DoctorUserRelation relation;
    String unreadJson;
    int doctorUnread;
    int userUnread;
    for (User user : relations) {
      relation = doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, user.getUserId());
      unreadJson = messageService.getReadInfoSeq(relation.getRelationId());
      doctorUnread = jsonParser.parseJsonInt(unreadJson, "DoctorUnread");
      userUnread = jsonParser.parseJsonInt(unreadJson, "UserUnread");
      messages.add(
          new MessageHandlePlus(
              user,
              relation.getRelationId(),
              doctorUnread,
              userUnread));
    }

    String json = jsonParser.toJsonFromEntityList(messages);

    json = jsonParser.removeKeyFromJson(json, "password");
    json = jsonParser.removeKeyFromJson(json, "email");
    json = jsonParser.removeKeyFromJson(json, "phone");
    json = jsonParser.removeKeyFromJson(json, "registrationDate");
    json = jsonParser.removeKeyFromJson(json, "lastLogin");
    json = jsonParser.removeKeyFromJson(json, "openid");
    return ResponseEntity.ok(json);
  }
}

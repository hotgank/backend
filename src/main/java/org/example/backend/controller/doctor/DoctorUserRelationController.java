package org.example.backend.controller.doctor;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.others.DoctorWithStatus;
import org.example.backend.entity.others.Message;
import org.example.backend.entity.others.Report;
import org.example.backend.service.doctor.DoctorUserRelationService;
import org.example.backend.service.others.MessageService;
import org.example.backend.service.others.ReportService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  private ReportService reportService;

  @Autowired
  private MessageService messageService;

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
    String json = jsonParser.toJsonFromEntityList(relations);
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
  public ResponseEntity<String> addDoctorUserRelation(@RequestBody DoctorUserRelation relation, HttpServletRequest request) {
    // 调用服务层来添加医患信息到数据库
    relation.setUserId((String) request.getAttribute("userId"));
    //根据userId查找用户医生关系，判断状态不为"rejected"的关系是否超过5个，如果大于5，则返回错误
    List<DoctorUserRelation> relations = doctorUserRelationService.getRelationsByUserId(relation.getUserId());
    int count = 0;
    for(DoctorUserRelation r : relations) {
      if(r.getRelationStatus().equals("pending") || r.getRelationStatus().equals("approved") || r.getRelationStatus().equals("removeBinding")) {
        count++;
      }
    }
    if(count >= 5) {
      return ResponseEntity.status(500).body("You have too many pending invitations");
    }
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
  public ResponseEntity<String> approveDoctorUserRelation(@RequestBody String jsonString, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String userId = jsonParser.parseJsonString(jsonString, "userId");
    DoctorUserRelation relation = doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, userId);
    if(relation == null){
      return ResponseEntity.status(500).body("You have no permission to access this user's reports");
    }
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
  public ResponseEntity<String> rejectDoctorUserRelation(@RequestBody String jsonString, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String userId = jsonParser.parseJsonString(jsonString, "userId");
    DoctorUserRelation relation = doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, userId);
    if(relation == null){
      return ResponseEntity.status(500).body("You have no permission to access this user's reports");
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

  //获取待审核列表
  @GetMapping("/selectPending")
  public ResponseEntity<String> selectPending(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来查询待审核信息
    List<DoctorUserRelation> relations = doctorUserRelationService.selectPendingPatients(doctorId, "pending");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relations));
  }

  @PostMapping("/selectReports")
  public ResponseEntity<String> selectReports(@RequestBody String jsonString,HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String userId = jsonParser.parseJsonString(jsonString, "userId");
    DoctorUserRelation relation = doctorUserRelationService.selectDoctorUserRelationByIDs(doctorId, userId);
    if(relation == null){
      return ResponseEntity.status(500).body("You have no permission to access this user's reports");
    }
    // 调用服务层来查询待审核信息
    List<Report> reports = reportService.selectByUserId(userId);
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(reports));
  }

      /**
     * 根据医生 ID 查询所有医生-用户关系
     * @param doctorId 医生的唯一标识
     * @return 医生-用户关系的列表
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getUserRelationsByDoctorId(@PathVariable String doctorId) {
        List<DoctorUserRelation> relations = doctorUserRelationService.getRelationsByDoctorId(doctorId);
        if (relations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No relations found for this doctor.");
        }
        return ResponseEntity.ok(relations);
    }

    /**
     * 根据用户 ID 查询所有用户-医生关系
     * @param userId 用户的唯一标识
     * @return 用户-医生关系的列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserRelationsByUserId(@PathVariable String userId) {
        List<DoctorUserRelation> relations = doctorUserRelationService.getRelationsByUserId(userId);
        if (relations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No relations found for this user.");
        }
        return ResponseEntity.ok(relations);
    }

    /**
     * 获取最后 30 条消息
     * @param relationId 医生-用户关系的唯一标识
     * @return 消息列表
     */
    @GetMapping("/{relationId}/messages/last30")
    public ResponseEntity<?> getLast30Messages(@PathVariable Integer relationId) {
        List<Message> messages = messageService.getLast30Messages(relationId);
        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No messages found.");
        }
        return ResponseEntity.ok(messages);
    }

    /**
     * 获取某条消息序号之后的所有消息
     * @param relationId 医生-用户关系的唯一标识
     * @param lastSeq 最后获取到的消息序号
     * @return 消息列表
     */
    @GetMapping("/{relationId}/messages/after/{lastSeq}")
    public ResponseEntity<?> getMessagesAfter(@PathVariable Integer relationId, @PathVariable Integer lastSeq) {
        List<Message> messages = messageService.getMessagesAfter(relationId, lastSeq);
        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No messages found.");
        }
        return ResponseEntity.ok(messages);
    }

    /**
     * 获取某条消息序号之前的 15 条消息
     * @param relationId 医生-用户关系的唯一标识
     * @param firstSeq 最早的消息序号
     * @return 消息列表
     */
    @GetMapping("/{relationId}/messages/before/{firstSeq}")
    public ResponseEntity<?> getMessagesBefore(@PathVariable Integer relationId, @PathVariable Integer firstSeq) {
        List<Message> messages = messageService.getMessagesBefore(relationId, firstSeq);
        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No messages found.");
        }
        return ResponseEntity.ok(messages);
    }


    //解除医生用户关系的申请
  @PostMapping("/removeBinding")
  public ResponseEntity<String> removeBinding(@RequestBody DoctorUserRelation relation, HttpServletRequest request) {
      String userId = (String) request.getAttribute("userId");
      relation.setUserId(userId);
      //先查询是否存在该关系且状态为approved
      DoctorUserRelation existRelation = doctorUserRelationService.selectDoctorUserRelationByIDs(relation.getDoctorId(), relation.getUserId());
      if(existRelation == null || !existRelation.getRelationStatus().equals("approved")){
          return ResponseEntity.status(500).body("You have no permission to remove this doctor user relation");
      }
      relation.setRelationStatus("removeBinding");
      boolean success = doctorUserRelationService.updateDoctorUserRelation(relation);
      if (success) {
          return ResponseEntity.ok("Successfully removed doctor user relation");
      } else {
          return ResponseEntity.status(500).body("Failed to remove doctor user relation");
      }

  }

  //同意解除医生用户关系的申请，删除关系
  @PostMapping("/agreeRemoveBinding")
  public ResponseEntity<String> agreeRemoveBinding(@RequestBody DoctorUserRelation relation, HttpServletRequest request) {
      String userId = (String) request.getAttribute("userId");
      relation.setUserId(userId);
      //先查询是否存在该关系且状态为removeBinding
      DoctorUserRelation existRelation = doctorUserRelationService.selectDoctorUserRelationByIDs(relation.getDoctorId(), relation.getUserId());
      if(existRelation == null || !existRelation.getRelationStatus().equals("removeBinding")){
          return ResponseEntity.status(500).body("You haveno permission to agree remove this doctor user relation");
      }
      boolean success = doctorUserRelationService.deleteDoctorUserRelation(existRelation);
      if (success) {
          return ResponseEntity.ok("Successfully removed doctor user relation");
      } else {
          return ResponseEntity.status(500).body("Failed to remove doctor user relation");
      }
  }

  //拒绝解除医生用户关系的申请
  @PostMapping("/rejectRemoveBinding")
  public ResponseEntity<String> rejectRemoveBinding(@RequestBody DoctorUserRelation relation, HttpServletRequest request) {
      String userId = (String) request.getAttribute("userId");
      relation.setUserId(userId);
      //先查询是否存在该关系且状态为removeBinding
      DoctorUserRelation existRelation = doctorUserRelationService.selectDoctorUserRelationByIDs(relation.getDoctorId(), relation.getUserId());
      if( existRelation == null || !existRelation.getRelationStatus().equals("removeBinding")){
          return ResponseEntity.status(500).body("You haveno permission to reject remove this doctor user relation");
      }
      existRelation.setRelationStatus("approved");
      boolean success = doctorUserRelationService.updateDoctorUserRelation(existRelation);
      if (success) {
          return ResponseEntity.ok("Successfully rejected remove doctor user relation");
      } else {
          return ResponseEntity.status(500).body("Failed to reject remove doctor user relation");
      }
  }

  //查询待处理的结束咨询申请
  @PostMapping("/selectRemoveBinding")
  public ResponseEntity<String> selectRemoveBinding(HttpServletRequest request) {
      String doctorId = (String) request.getAttribute("userId");
      List<DoctorUserRelation> relationList = doctorUserRelationService.selectRemoveBindingRelations(doctorId);
      return ResponseEntity.ok(jsonParser.toJsonFromEntityList(relationList));
  }


}

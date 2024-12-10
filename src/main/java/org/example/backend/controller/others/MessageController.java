package org.example.backend.controller.others;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.example.backend.dto.LastMessage;
import org.example.backend.dto.SendMessageRequest;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.others.Message;
import org.example.backend.service.others.MessageService;
import org.example.backend.service.doctor.DoctorUserRelationService;
import org.example.backend.util.UrlUtil;
import org.example.backend.util.MultipartFileUtil;
import org.example.backend.util.RedisUtil;
import org.example.backend.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/messages")
public class MessageController {
  @Autowired private UrlUtil urlUtil;

  private static final Logger log = LoggerFactory.getLogger(MessageController.class);
  @Autowired private MessageService messageService;

  @Autowired private DoctorUserRelationService doctorUserRelationService;

  @Autowired private MultipartFileUtil multipartFileUtil;

  @Autowired private RedisUtil redisUtil;

  @Autowired private JsonParser jsonParser;

  // 支持的文件类型（根据 MIME 类型或文件扩展名）
  private static final List<String> IMAGE_TYPES = List.of("image/jpeg", "image/png", "image/gif");
  private static final List<String> VIDEO_TYPES = List.of("video/mp4", "video/avi", "video/mpeg");
  private static final List<String> TEXT_TYPES = List.of("text/plain", "application/json");

  @GetMapping("/lastMessage")
  public ResponseEntity<List<LastMessage>> getLastMessage(HttpServletRequest httpServletRequest) {
    String userId = (String) httpServletRequest.getAttribute("userId");

    // 查询用户的所有医生
    List<Doctor> doctors = doctorUserRelationService.selectMyDoctors(userId);
    List<LastMessage> lastMessages = new ArrayList<>(); // 创建 LastMessage 列表

    // 遍历每个医生
    for (Doctor doctor : doctors) {
      // 如果医生有头像 URL，将其转换为 Base64

      // 获取医生和用户之间的关系
      DoctorUserRelation relation =
          doctorUserRelationService.selectDoctorUserRelationByIDs(doctor.getDoctorId(), userId);
      if (relation != null) {
        // 获取最后一条消息
        Message message = messageService.getLastMessage(relation.getRelationId());
        if (message != null) {
          // 创建 LastMessage 对象并添加到列表中
          LastMessage lastMessage = new LastMessage(doctor, message);
          lastMessages.add(lastMessage);
        }
      }
    }

    // 如果有数据，返回 LastMessage 列表，否则返回空数据
    if (!lastMessages.isEmpty()) {
      return ResponseEntity.ok(lastMessages);
    } else {
      return ResponseEntity.noContent().build(); // 返回 HTTP 204 状态码
    }
  }

  @GetMapping("/last30/{relationId}")
  public ResponseEntity<List<Message>> getLast30Messages(
      @PathVariable Integer relationId, HttpServletRequest httpServletRequest) {
    String Id = (String) httpServletRequest.getAttribute("userId");

    DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
    if (!(relation.getDoctorId().equals(Id) || relation.getUserId().equals(Id))) {
      return ResponseEntity.status(500).body(null);
    }
    return ResponseEntity.ok(messageService.getLast30Messages(relationId));
  }

  @GetMapping("/after/{relationId}/{messageSeq}")
  public ResponseEntity<List<Message>> getMessagesAfterSeq(
      @PathVariable Integer relationId,
      @PathVariable Integer messageSeq,
      HttpServletRequest httpServletRequest) {
    String Id = (String) httpServletRequest.getAttribute("userId");

    DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
    if (!(relation.getDoctorId().equals(Id) || relation.getUserId().equals(Id))) {
      return ResponseEntity.status(500).body(null);
    }
    return ResponseEntity.ok(messageService.getMessagesAfterSeq(relationId, messageSeq));
  }

  @GetMapping("/before/{relationId}/{messageSeq}")
  public ResponseEntity<List<Message>> getMessagesBeforeSeq(
      @PathVariable Integer relationId,
      @PathVariable Integer messageSeq,
      HttpServletRequest httpServletRequest) {
    String Id = (String) httpServletRequest.getAttribute("userId");

    DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
    if (!(relation.getDoctorId().equals(Id) || relation.getUserId().equals(Id))) {
      return ResponseEntity.status(500).body(null);
    }
    return ResponseEntity.ok(messageService.getMessagesBeforeSeq(relationId, messageSeq));
  }

  @PostMapping("/send")
  public ResponseEntity<Message> sendMessage(
      @RequestBody SendMessageRequest request, HttpServletRequest httpServletRequest) {
    String Id = (String) httpServletRequest.getAttribute("userId");
    int relationId = request.getRelationId();
    DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
    if (relation == null) {
      return ResponseEntity.status(500).body(null);
    }
    if (!relation.getRelationStatus().equals("approved")) {
      return ResponseEntity.status(500).body(null);
    }
    if (relation.getDoctorId().equals(Id)) {
      request.setSenderType("doctor");
    } else if (relation.getUserId().equals(Id)) {
      request.setSenderType("user");
    } else {
      return ResponseEntity.status(500).body(null);
    }

    Message message =
        messageService.sendMessage(
            request.getRelationId(),
            request.getSenderType(),
            request.getMessageText(),
            request.getMessageType(),
            request.getUrl());
    return ResponseEntity.ok(message);
  }

  @PostMapping("/sendFile")
  public ResponseEntity<Message> sendFile(
      Integer relationId,
      String messageText,
      MultipartFile file,
      HttpServletRequest httpServletRequest) {
    // 获取当前用户的 ID
    String userId = (String) httpServletRequest.getAttribute("userId");

    log.info("relationId: " + relationId);
    // 获取医生-用户关系
    DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
    if (relation == null) {
      return ResponseEntity.status(500).body(null); // 如果没有找到关系，返回 500 错误
    }

    // 校验关系状态
    if (!relation.getRelationStatus().equals("approved")) {
      return ResponseEntity.status(500).body(null); // 如果关系没有通过审批，返回 500 错误
    }

    // 设置消息发送者类型
    String senderType = null;
    if (relation.getDoctorId().equals(userId)) {
      senderType = "doctor"; // 如果是医生发送消息
    } else if (relation.getUserId().equals(userId)) {
      senderType = "user"; // 如果是用户发送消息
    } else {
      return ResponseEntity.status(500).body(null); // 如果发送者不在关系中，返回 500 错误
    }

    // 判断文件类型并设置 message_type
    String messageType = determineMessageType(file);

    // 处理文件上传并获取文件 URL
    String fileUrl = multipartFileUtil.saveMultipartFile(file, "MessageFiles/" + relationId + "/");
    if (fileUrl == null) {
      return ResponseEntity.status(400).body(null); // 如果文件上传失败，返回 400 错误
    }

    // 创建消息记录
    Message message =
        messageService.sendMessage(
            relationId,
            senderType,
            messageText, // 文本消息内容
            messageType, // 自动推断的消息类型
            fileUrl // 文件 URL
            );

    // 返回成功响应
    return ResponseEntity.ok(message);
  }

  // 判断文件类型（根据 MIME 类型或扩展名）
  private String determineMessageType(MultipartFile file) {
    String contentType = file.getContentType();
    if (contentType != null) {
      if (IMAGE_TYPES.contains(contentType)) {
        return "image"; // 图片
      } else if (VIDEO_TYPES.contains(contentType)) {
        return "video"; // 视频
      } else if (TEXT_TYPES.contains(contentType)) {
        return "text"; // 文本
      }
    }
    return "file"; // 默认是文件类型
  }

  @PostMapping("/getReadInfo")
  public ResponseEntity<String> getReadInfo(
      @RequestBody String jsonString, HttpServletRequest httpServletRequest) {
    String userId = (String) httpServletRequest.getAttribute("userId");
    int relationId = jsonParser.parseJsonInt(jsonString, "relationId");
    int ReadSeq = jsonParser.parseJsonInt(jsonString, "ReadSeq");
    DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
    if (relation == null) {
      log.error("relationId: " + relationId + " not found");
      return ResponseEntity.status(500).body(null);
    }
    String senderType = null;
    if (relation.getDoctorId().equals(userId)) {
      senderType = "doctor";
    } else if (relation.getUserId().equals(userId)) {
      senderType = "user";
    } else {
      return ResponseEntity.status(500).body(null);
    }
    redisUtil.setNoExpireKey(relationId + "_" + senderType, ReadSeq);
    int DoctorUnread =
        messageService.countUnreadMessages(
            relationId, redisUtil.getIntegerFromRedis(relationId + "_doctor"), "user");
    int UserUnread =
        messageService.countUnreadMessages(
            relationId, redisUtil.getIntegerFromRedis(relationId + "_user"), "doctor");
    return ResponseEntity.ok(
        "{\"DoctorUnread\": " + DoctorUnread + ", \"UserUnread\":" + UserUnread + "}");
  }

  @PostMapping("/getUnReadInfo")
  public ResponseEntity<String> startReadInfo(
      @RequestBody String jsonString, HttpServletRequest httpServletRequest) {
    String userId = (String) httpServletRequest.getAttribute("userId");
    int relationId = jsonParser.parseJsonInt(jsonString, "relationId");
    DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
    if (relation == null) {
      log.error("relationId: " + relationId + " not found");
      return ResponseEntity.status(500).body(null);
    }
    String senderType = null;
    if (relation.getDoctorId().equals(userId)) {
      senderType = "doctor";
    } else if (relation.getUserId().equals(userId)) {
      senderType = "user";
    } else {
      return ResponseEntity.status(500).body(null);
    }
    int DoctorUnread =
        messageService.countUnreadMessages(
            relationId, redisUtil.getIntegerFromRedis(relationId + "_doctor"), "user");
    int UserUnread =
        messageService.countUnreadMessages(
            relationId, redisUtil.getIntegerFromRedis(relationId + "_user"), "doctor");
    return ResponseEntity.ok(
        "{\"DoctorUnread\": " + DoctorUnread + ", \"UserUnread\":" + UserUnread + "}");
  }

  // 更新Redis中的已读消息的序列号
  @PostMapping("/updateReadInfo")
  public ResponseEntity<String> updateReadInfo(
      @RequestBody String jsonString, HttpServletRequest httpServletRequest) {
    String userId = (String) httpServletRequest.getAttribute("userId");
    int relationId = jsonParser.parseJsonInt(jsonString, "relationId");
    int ReadSeq = jsonParser.parseJsonInt(jsonString, "ReadSeq");
    DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
    if (relation == null) {
      log.error("relationId: " + relationId + " not found");
      return ResponseEntity.status(500).body(null);
    }
    String senderType = null;
    if (relation.getDoctorId().equals(userId)) {
      senderType = "doctor";
    } else if (relation.getUserId().equals(userId)) {
      senderType = "user";
    } else {
      return ResponseEntity.status(500).body(null);
    }
    redisUtil.setNoExpireKey(relationId + "_" + senderType, ReadSeq);
    return ResponseEntity.ok("success");
  }

  @GetMapping("/TodayCousultationUserCount")
  public ResponseEntity<String> TodayCousultationUserCount(
      @RequestParam String doctorId, HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok("10");
  }
  @GetMapping("/TodayCousultationDoctorCount")
  public ResponseEntity<Integer> TodayCousultationDoctorCount(HttpServletRequest httpServletRequest) {
    String userId = (String) httpServletRequest.getAttribute("userId");
    return ResponseEntity.ok(messageService.TodayCousultationUserCount(userId));
  }
}

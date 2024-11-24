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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/messages")
public class MessageController {
  @Autowired
  private UrlUtil urlUtil;

  private static final Logger log = LoggerFactory.getLogger(MessageController.class);
  @Autowired
    private MessageService messageService;

  @Autowired
  private DoctorUserRelationService doctorUserRelationService;

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
        DoctorUserRelation relation = doctorUserRelationService.selectDoctorUserRelationByIDs(doctor.getDoctorId(), userId);
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
    public ResponseEntity<List<Message>> getLast30Messages(@PathVariable Integer relationId, HttpServletRequest httpServletRequest) {
                String Id = (String) httpServletRequest.getAttribute("userId");

      DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
      if (!(relation.getDoctorId().equals(Id) || relation.getUserId().equals(Id))) {
        return ResponseEntity.status(500).body(null);
      }
      return ResponseEntity.ok(messageService.getLast30Messages(relationId));
    }

    @GetMapping("/after/{relationId}/{messageSeq}")
    public ResponseEntity<List<Message>> getMessagesAfterSeq(@PathVariable Integer relationId, @PathVariable Integer messageSeq,HttpServletRequest httpServletRequest) {
                String Id = (String) httpServletRequest.getAttribute("userId");

      DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
      if (!(relation.getDoctorId().equals(Id) || relation.getUserId().equals(Id))) {
        return ResponseEntity.status(500).body(null);
      }
      return ResponseEntity.ok(messageService.getMessagesAfterSeq(relationId, messageSeq));
    }

    @GetMapping("/before/{relationId}/{messageSeq}")
    public ResponseEntity<List<Message>> getMessagesBeforeSeq(@PathVariable Integer relationId, @PathVariable Integer messageSeq,HttpServletRequest httpServletRequest) {
        String Id = (String) httpServletRequest.getAttribute("userId");

      DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
      if (!(relation.getDoctorId().equals(Id) || relation.getUserId().equals(Id))) {
        return ResponseEntity.status(500).body(null);
      }
      return ResponseEntity.ok(messageService.getMessagesBeforeSeq(relationId, messageSeq));
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest request,
        HttpServletRequest httpServletRequest){
      String Id = (String) httpServletRequest.getAttribute("userId");
      int relationId = request.getRelationId();
      DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
      if(relation == null){
        return ResponseEntity.status(500).body(null);
      }
      if(!relation.getRelationStatus().equals("approved")){
        return ResponseEntity.status(500).body(null);
      }
      if (relation.getDoctorId().equals(Id)){
        request.setSenderType("doctor");
      } else if (relation.getUserId().equals(Id)) {
        request.setSenderType("user");
      }else {
        return ResponseEntity.status(500).body(null);
      }

      Message message = messageService.sendMessage(
                request.getRelationId(),
                request.getSenderType(),
                request.getMessageText(),
                request.getMessageType(),
                request.getUrl()
        );
        return ResponseEntity.ok(message);
    }
}


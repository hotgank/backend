package org.example.backend.controller.others;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.example.backend.dto.SendMessageRequest;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.entity.others.Message;
import org.example.backend.service.others.MessageService;
import org.example.backend.service.doctor.DoctorUserRelationService;
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

  private static final Logger log = LoggerFactory.getLogger(MessageController.class);
  @Autowired
    private MessageService messageService;

  @Autowired
  private DoctorUserRelationService doctorUserRelationService;

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
      log.info("Id: " + Id);
      int relationId = request.getRelationId();
      DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
      log.info("relation: " + relation);
      if(relation == null){
        return ResponseEntity.status(500).body(null);
      }
      log.info("relationId: " + relationId);
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


package org.example.backend.controller.doctor;

import java.util.List;
import org.example.backend.entity.user.Message;
import org.example.backend.service.user.MessageService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctorMessage")
public class DoctorMessageController {

  @Autowired
  MessageService messageService;

  @PostMapping("/select")
  public ResponseEntity<String> select(@RequestBody String consultationJson) {
    String doctorId = JsonParser.parseJsonString(consultationJson, "doctorId");
    String userId = JsonParser.parseJsonString(consultationJson, "userId");
    List<Message> messages = messageService.selectMessagesById(doctorId, userId);
    if(messages != null){
      return ResponseEntity.ok(messages.toString());
    }else{
      return ResponseEntity.status(500).body("Failed to find messages");
    }
  }
}

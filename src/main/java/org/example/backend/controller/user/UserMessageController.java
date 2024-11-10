package org.example.backend.controller.user;

import org.example.backend.entity.user.Message;
import org.example.backend.service.others.MessageService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userMessage")
public class UserMessageController {

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

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Message message) {
        message.setSenderType("user");
        int result = messageService.insertMessage(message);
        if(result > 0){
            return ResponseEntity.ok("Added successfully");
        }else{
            return ResponseEntity.status(500).body("Failed to add message");
        }
    }
}

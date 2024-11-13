package org.example.backend.controller.doctor;

import org.example.backend.entity.others.Consultation;
import org.example.backend.service.others.ConsultationService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctorConsultation")
public class DoctorConsultationController {

  @Autowired
  ConsultationService consultationService;

  @Autowired
  private JsonParser jsonParser;

  @PostMapping("/add")
  public ResponseEntity<String> addConsultation(@RequestBody String consultationJson) {
    String doctorId = jsonParser.parseJsonString(consultationJson, "doctorId");
    String userId = jsonParser.parseJsonString(consultationJson, "userId");
    int consultationId = consultationService.insertConsultation(doctorId, userId);
    if(consultationId > 0){
      return ResponseEntity.ok("Consultation added successfully");
    }else{
      return ResponseEntity.badRequest().body("Failed to add consultation");
    }
  }

  @PostMapping("/select")
  public ResponseEntity<String> selectConsultation(@RequestBody String consultationJson) {
    String doctorId = jsonParser.parseJsonString(consultationJson, "doctorId");
    String userId = jsonParser.parseJsonString(consultationJson, "userId");
    Consultation consultation =
        consultationService.selectConsultationByDoctorIdAndUserId(doctorId, userId);
    if(consultation != null){
      return ResponseEntity.ok(jsonParser.toJsonFromEntity(consultation));
    }else{
      return ResponseEntity.badRequest().body("Failed to select consultation");
    }
  }

}

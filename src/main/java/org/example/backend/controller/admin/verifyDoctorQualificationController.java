package org.example.backend.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.service.admin.VerifyDoctorQualificationService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verifyDoctor")
public class verifyDoctorQualificationController {

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private VerifyDoctorQualificationService verifyDoctorQualificationService;

    @GetMapping("/selectAll")
    public ResponseEntity <?> selectAll(HttpServletRequest request){
        try{
            String adminId = (String) request.getAttribute("userId");
            if (adminId != null && !adminId.isEmpty() && adminId.charAt(0) == 'A') {
                return ResponseEntity.ok(jsonParser.toJsonFromEntityList(verifyDoctorQualificationService.selectAll()));
            }
            return ResponseEntity.status(400).body(null);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/approve")
    public ResponseEntity <?> approve(@RequestBody String auditIdJson, HttpServletRequest request){
        try {
            String adminId = (String) request.getAttribute("userId");
            if (adminId != null && !adminId.isEmpty() && adminId.charAt(0) == 'A') {
                String auditId = jsonParser.parseJsonString(auditIdJson, "auditId");
                return ResponseEntity.ok(verifyDoctorQualificationService.approve(auditId, adminId));
            } else {
                return ResponseEntity.status(400).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/reject")
    public ResponseEntity <?> reject(@RequestBody String auditIdJson, HttpServletRequest request){
        try {
            String adminId = (String) request.getAttribute("userId");
            if (adminId != null && !adminId.isEmpty() && adminId.charAt(0) == 'A') {
                String auditId = jsonParser.parseJsonString(auditIdJson, "auditId");
                String comment = jsonParser.parseJsonString(auditIdJson, "comment");
                return ResponseEntity.ok(verifyDoctorQualificationService.reject(auditId, adminId, comment));
            } else {
                return ResponseEntity.status(400).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}

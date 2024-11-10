package org.example.backend.controller.user;

import org.example.backend.entity.user.Report;
import org.example.backend.service.others.ReportService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userReport")
public class UserReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/selectByChildId")
    public ResponseEntity<String> selectByChildId(@RequestBody String childIdJson) {
        String childId = JsonParser.parseJsonString(childIdJson, "childId");
        List<Report> reports = reportService.selectByChildId(childId);
        if(reports != null){
            return ResponseEntity.ok(reports.toString());
        }else{
            return ResponseEntity.status(500).body("Failed to find reports");
        }
    }

}

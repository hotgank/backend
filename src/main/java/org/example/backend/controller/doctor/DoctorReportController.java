package org.example.backend.controller.doctor;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.entity.others.Report;
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
@RequestMapping("/api/doctorReport")
public class DoctorReportController {

  @Autowired private ReportService reportService;

  @Autowired private JsonParser jsonParser;

  private static final org.apache.logging.log4j.Logger logger =
      org.apache.logging.log4j.LogManager.getLogger(DoctorReportController.class);

  @PostMapping("/selectByChildId")
  public ResponseEntity<String> selectByChildId(HttpServletRequest request) {
    String childId = (String) request.getAttribute("userId");
    List<Report> reports = reportService.selectByChildId(childId);
    if (reports != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntityList(reports));
    } else {
      return ResponseEntity.status(500).body("Failed to find reports");
    }
  }

  @PostMapping("/comment")
  public ResponseEntity<String> comment(@RequestBody Report report, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    logger.info("doctorId: " + doctorId);
    logger.info("report: " + report);
    boolean success =
        reportService.doctorEditReport(report.getReportId(), report.getComment(), doctorId);
    if (success) {
      return ResponseEntity.ok("Comment successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to comment");
    }
  }
}

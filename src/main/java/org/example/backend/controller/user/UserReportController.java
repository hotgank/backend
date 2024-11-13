package org.example.backend.controller.user;

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
@RequestMapping("/api/userReport")
public class UserReportController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private JsonParser jsonParser;

    @PostMapping("/selectByChildId")
    public ResponseEntity<List<Report>> selectByChildId(@RequestBody String childIdJson) {
        String childId = jsonParser.parseJsonString(childIdJson, "childId");
        List<Report> reports = reportService.selectByChildId(childId);
        if(reports != null){
            return ResponseEntity.ok(reports);
        }else{
            return ResponseEntity.status(500).body(null);
        }
    }
    // 根据reportId删除
    @PostMapping("/deleteByReportId")
    public ResponseEntity<String> deleteByReportId(@RequestBody String reportIdJson) {
        int reportId = jsonParser.parseJsonInt(reportIdJson, "reportId");
        boolean isDeleted = reportService.deleteByReportId(reportId);
        if (isDeleted) {
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.status(404).body("未找到相关数据");
        }
    }
    //根据childId删除报告
    @PostMapping("/deleteByChildId")
    public ResponseEntity<String> deleteByChildId(@RequestBody String childIdJson) {
        String childId = jsonParser.parseJsonString(childIdJson, "childId");
        boolean isDeleted = reportService.deleteByChildId(childId);
        if (isDeleted) {
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.status(404).body("未找到相关数据");
        }
    }

    //create报告
    @PostMapping("/createReport")
    public ResponseEntity<String> createReport(@RequestBody Report report) {
        int insert = reportService.insertReport(report);
        if (insert > 0) {
            System.out.println("Report inserted successfully: " + insert);
            return ResponseEntity.ok("添加成功");
        } else {
            return ResponseEntity.status(500).body("添加失败");
        }
    }

    //update报告
    @PostMapping("/updateReport")
    public ResponseEntity<String> updateReport(@RequestBody Report report) {
        boolean update = reportService.updateReport(report);
        if (update) {
            return ResponseEntity.ok("更新成功");
        } else {
            return ResponseEntity.status(500).body("更新失败");
        }
    }
}

package org.example.backend.controller.others;


import java.util.List;
import org.example.backend.entity.others.DetectionAPI;
import org.example.backend.service.others.DetectionAPIService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/detectionAPI")
public class DetectionAPIController {

  @Autowired private DetectionAPIService detectionAPIService;

  @Autowired private JsonParser jsonParser;

  @PostMapping("/add")
  public ResponseEntity<String> addDetectionAPI(@RequestBody DetectionAPI detectionAPI) {
    int result = detectionAPIService.insertDetectionAPI(detectionAPI);
    if (result == 1) {
      return ResponseEntity.ok("添加成功");
    } else {
      return ResponseEntity.badRequest().body("添加失败");
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<String> deleteDetectionAPI(@RequestBody String JsonString) {
    int result = detectionAPIService.deleteDetectionAPIById(jsonParser.parseJsonInt(JsonString, "apiId"));
    if (result == 1) {
      return ResponseEntity.ok("删除成功");
    } else {
      return ResponseEntity.badRequest().body("删除失败");
    }
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateDetectionAPI(@RequestBody DetectionAPI detectionAPI) {
    int result = detectionAPIService.updateDetectionAPI(detectionAPI);
    if (result == 1) {
      return ResponseEntity.ok("更新成功");
    } else {
      return ResponseEntity.badRequest().body("更新失败");
    }
  }

  @GetMapping("/getAll")
  public ResponseEntity<String> getAllDetectionAPI() {
    List<DetectionAPI> detectionAPIList = detectionAPIService.selectAll();
    if (detectionAPIList != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntityList(detectionAPIList));
    } else {
      return ResponseEntity.badRequest().body("获取失败");
    }
  }
}

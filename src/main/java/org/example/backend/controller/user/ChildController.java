package org.example.backend.controller.user;

import java.util.List;
import org.example.backend.entity.user.Child;
import org.example.backend.service.user.ChildService;
import org.example.backend.util.ExcelReader;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/children")
public class ChildController {

  @Autowired
  private ChildService childService;

  @Autowired
  private ExcelReader excelReader;

  @Autowired
  private JsonParser jsonParser;

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAll() {

    // 调用服务层来查询所有孩子信息
    String result = childService.selectAll().toString();

    return ResponseEntity.ok(result);
  }

  @PostMapping("/selectById")
  public ResponseEntity<String> selectById(@RequestBody String childIdJson) {
    String childId = jsonParser.parseJsonString(childIdJson, "childId");
    // 调用服务层来根据childId查询孩子信息
    Child selectedChild = childService.selectById(childId);

    if (selectedChild != null) {
      return ResponseEntity.ok(selectedChild.toString());
    } else {
      return ResponseEntity.status(500).body("Failed to add child information");
    }
  }

  // 处理添加孩子信息的请求
  @PostMapping("/add")
  public ResponseEntity<String> addChild(@RequestBody Child child) {

    // 调用服务层来添加孩子信息到数据库
    String result = childService.insert(child);

    if (result != null) {
      return ResponseEntity.ok("Child information added successfully, childId: " + result);
    } else {
      return ResponseEntity.status(500).body("Failed to add child information");
    }
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateChild(@RequestBody Child child) {

    // 调用服务层来更新孩子信息
    boolean success = childService.update(child);

    if (success) {
      return ResponseEntity.ok("Child information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update child information");
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<String> deleteChild(@RequestBody String childIdJson) {
    String childId = jsonParser.parseJsonString(childIdJson, "childId");
    // 调用服务层来删除孩子信息
    boolean success = childService.delete(childId);

    if (success) {
      return ResponseEntity.ok("Child information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to delete child information");
    }
  }

  @PostMapping("/insertAll")
  public ResponseEntity<String> insertAllChildren(@RequestBody String urlJson) {
    String url = jsonParser.parseJsonString(urlJson, "url");
    List<Child> children = excelReader.readExcel(url, Child.class);
    boolean success = childService.insertAllChildren(children);
    if (success) {
      return ResponseEntity.ok("Children information added successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to add children information");
    }
  }
}

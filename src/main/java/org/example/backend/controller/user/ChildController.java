package org.example.backend.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.example.backend.entity.user.Child;
import org.example.backend.entity.user.ParentChildRelation;
import org.example.backend.service.serviceImpl.user.ParentChildRelationImpl;
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
  private ParentChildRelationImpl parentChildRelationService;

  @Autowired
  private JsonParser jsonParser;

  @Autowired
  private ExcelReader excelReader;

  // 处理创建孩子和创建用户的关系请求
  @PostMapping("/createChild")
  public ResponseEntity<String> createChild(@RequestBody Child child, HttpServletRequest request) {
    try {
      //从请求中获取用户ID
      String userId = (String) request.getAttribute("userId");

      //调用服务层来添加孩子信息到数据库
      String childId = childService.insert(child);
      //调用服务层来创建孩子和用户的关系
      ParentChildRelation relation = new ParentChildRelation();
      relation.setUserId(userId);
      relation.setChildId(childId);
      relation.setRelationType("家长-孩子");
      relation.setCreatedAt(java.time.LocalDateTime.now());
      int relationId = parentChildRelationService.createRelation(relation);
      relation.setRelationId(relationId);
      return ResponseEntity.ok("{\"relationId\":\""+relation.getRelationId()+"\"}");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to create child information");
    }
  }

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAll() {

    // 调用服务层来查询所有孩子信息
    String result = childService.selectAll().toString();

    return ResponseEntity.ok(result);
  }

  // 处理根据childId查询孩子信息的请求
  @GetMapping("/selectById")
  public ResponseEntity<String> selectById(@RequestBody String childIdJson) {
    String childId = jsonParser.parseJsonString(childIdJson, "childId");
    // 调用服务层来根据childId查询孩子信息
    Child selectedChild = childService.selectById(childId);

    if (selectedChild != null) {
      // 假设 selectedChild.getBirthdate() 返回的是 java.util.Date 类型，转换为 LocalDate
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String formattedBirthdate = selectedChild.getBirthdate().toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate()
          .format(formatter);

      return ResponseEntity.ok("{\"name\":\"" + selectedChild.getName()
          + "\",\"school\":\"" + selectedChild.getSchool()
          + "\",\"gender\":\"" + selectedChild.getGender()
          + "\",\"birthdate\":\"" + formattedBirthdate
          + "\",\"height\":\"" + selectedChild.getHeight()
          + "\",\"weight\":\"" + selectedChild.getWeight()
          + "\"}");
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

  // 处理更新孩子信息的请求
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

  //删除档案
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

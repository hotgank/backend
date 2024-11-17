package org.example.backend.controller.others;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.others.HealthArticle;
import org.example.backend.service.others.HealthArticleService;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/healthArticle")
public class HealthArticleController {

  @Autowired
  private HealthArticleService healthArticleService;
  @Autowired
  private JsonParser jsonParser;
  @Autowired
  private DoctorService doctorService;
  @PostMapping("/add")
  public ResponseEntity<String> addHealthArticle(@RequestBody String healthArticle,
      HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    HealthArticle healthArticle1=new HealthArticle();
    healthArticle1.setDoctorId(doctorId);
    healthArticle1.setTitle(jsonParser.parseJsonString(healthArticle,"title"));
    healthArticle1.setContent(jsonParser.parseJsonString(healthArticle,"content"));
    healthArticle1.setType(jsonParser.parseJsonString(healthArticle,"type"));
    healthArticle1.setStatus("未审核");
    healthArticle1.setPublishDate(new Date());
    boolean result=healthArticleService.createHealthArticle(healthArticle1);
    if(result)
      return ResponseEntity.ok("Successfully commit health article");
    else return ResponseEntity.status(500).body("Failed to commit health article");
  }
  @PostMapping("/getAll")
  public ResponseEntity<String> getAllHealthArticle() {
  String jsonString = jsonParser.toJsonFromEntityList(healthArticleService.getAll());
  jsonString = jsonParser.removeKeyFromJson(jsonString, "doctorId");
  return ResponseEntity.ok(jsonParser.toJsonFromEntityList(healthArticleService.getAll())); }

  @PostMapping("/getDoctorByArticleId")
  public ResponseEntity<String> getDoctorByArticleId(@RequestBody String healthArticle) {
    int articleId=jsonParser.parseJsonInt(healthArticle,"articleId");
    if(healthArticleService.getById(articleId)==null)
      return ResponseEntity.status(404).body("Not Found");
    String doctorId=healthArticleService.getById(articleId).getDoctorId();
    Doctor doctor=doctorService.selectById(doctorId);
    String jsonString=jsonParser.toJsonFromEntity(doctor);
    jsonString = jsonParser.removeKeyFromJson(jsonString, "password");
    jsonString = jsonParser.removeKeyFromJson(jsonString, "doctorId");
    jsonString = jsonParser.removeKeyFromJson(jsonString, "registrationDate");
    jsonString = jsonParser.removeKeyFromJson(jsonString, "lastLogin");
    jsonString = jsonParser.removeKeyFromJson(jsonString, "status");
    jsonString = jsonParser.removeKeyFromJson(jsonString, "avatarUrl");
    jsonString = jsonParser.removeKeyFromJson(jsonString, "phone");
    jsonString = jsonParser.removeKeyFromJson(jsonString, "email");
    jsonString = jsonString.substring(0,jsonString.length()-1);
    return ResponseEntity.ok(jsonString+",\"avatar\":\"" + doctorService.getAvatarBase64(doctorId)+"\"}");
  }

  @GetMapping("/myArticles")
  public ResponseEntity<String> getMyArticles(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String jsonString = jsonParser.toJsonFromEntityList(healthArticleService.getByDoctorId(doctorId));
    jsonString = jsonParser.removeKeyFromJson(jsonString, "doctorId");
    jsonString = jsonParser.removeKeyFromJson(jsonString, "publishDate");
    jsonString = jsonParser.removeKeyFromJson(jsonString, "status");
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(healthArticleService.getByDoctorId(doctorId)));
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateHealthArticle(@RequestBody String healthArticle, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    Doctor doctor=doctorService.selectById(doctorId);
    if(doctor==null)
      return ResponseEntity.status(403).body("Forbidden");
    int articleId=jsonParser.parseJsonInt(healthArticle,"articleId");
    HealthArticle healthArticle1=healthArticleService.getById(articleId);
    if(!doctorId.equals(healthArticle1.getDoctorId())){
      return ResponseEntity.status(403).body("Forbidden");
    }
    healthArticle1.setTitle(jsonParser.parseJsonString(healthArticle,"title"));
    healthArticle1.setContent(jsonParser.parseJsonString(healthArticle,"content"));
    healthArticle1.setType(jsonParser.parseJsonString(healthArticle,"type"));
    healthArticle1.setStatus("未审核");

    boolean result=healthArticleService.updateHealthArticle(healthArticle1);
    if(result)
      return ResponseEntity.ok("Successfully updated health article");
    else return ResponseEntity.status(500).body("Failed to update health article");
  }

  @PostMapping("/details")
  public ResponseEntity<String> getHealthArticleDetails(@RequestBody String healthArticle) {
    int articleId=jsonParser.parseJsonInt(healthArticle,"articleId");
    HealthArticle healthArticle1=healthArticleService.getById(articleId);
    if(healthArticle1==null){
      return ResponseEntity.status(404).body("Not Found");
    }
    String jsonString=jsonParser.toJsonFromEntity(healthArticle1);
    jsonString = jsonParser.removeKeyFromJson(jsonString, "doctorId");
    return ResponseEntity.ok(jsonString);
  }
}
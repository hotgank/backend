package org.example.backend.controller.others;

import org.example.backend.entity.others.HealthArticle;
import org.example.backend.service.others.HealthArticleService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
  @PostMapping("/add")
  public ResponseEntity<String> addHealthArticle(@RequestBody HealthArticle healthArticle) {
    boolean result=healthArticleService.createHealthArticle(healthArticle);
    if(result)
      return ResponseEntity.ok("Successfully commit health article");
    else return ResponseEntity.status(500).body("Failed to commit health article");
  }
  @PostMapping("/getAll")
  public ResponseEntity<String> getAllHealthArticle() {

  return ResponseEntity.ok(jsonParser.toJsonFromEntityList(healthArticleService.getAll())); }

}

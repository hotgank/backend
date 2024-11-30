package org.example.backend.controller.others;

import java.util.List;
import org.example.backend.entity.others.School;
import org.example.backend.service.others.SchoolService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school")
public class SchoolController {
  @Autowired private SchoolService schoolService;

  @Autowired private JsonParser jsonParser;

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAllSchools() {
    List<School> schools = schoolService.selectAllSchools();

    if (schools != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntityList(schools));
    } else {
      return ResponseEntity.status(500).body("Failed to find schools");
    }
  }
}

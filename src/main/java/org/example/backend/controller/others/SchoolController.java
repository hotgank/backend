package org.example.backend.controller.others;

import java.util.List;
import org.example.backend.entity.others.School;
import org.example.backend.service.others.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school")
public class SchoolController {
  @Autowired
  private SchoolService schoolService;

  @GetMapping("/selectAll")
  public List<School> selectAllSchools() {
    return schoolService.selectAllSchools();
  }
}

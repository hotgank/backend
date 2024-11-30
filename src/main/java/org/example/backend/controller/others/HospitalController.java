package org.example.backend.controller.others;

import java.util.List;
import org.example.backend.entity.others.Hospital;
import org.example.backend.service.others.HospitalService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {
  @Autowired private HospitalService hospitalService;

  @Autowired private JsonParser jsonParser;

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAllHospitals() {
    List<Hospital> hospitals = hospitalService.selectAllHospitals();

    if (hospitals != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntityList(hospitals));
    } else {
      return ResponseEntity.status(500).body("Failed to find hospitals");
    }
  }
}

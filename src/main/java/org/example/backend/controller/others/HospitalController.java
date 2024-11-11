package org.example.backend.controller.others;

import java.util.List;
import org.example.backend.entity.others.Hospital;
import org.example.backend.service.others.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {
  @Autowired
  private HospitalService hospitalService;

  @RequestMapping("/selectAll")
  public List<Hospital> selectAllHospitals() {
    return hospitalService.selectAllHospitals();
  }
}

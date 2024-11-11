package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.Hospital;

public interface HospitalService {

  List<Hospital> selectAllHospitals();
}

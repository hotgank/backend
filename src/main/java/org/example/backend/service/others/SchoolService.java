package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.School;

public interface SchoolService {
  List<School> selectAllSchools();
}

package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.Unit;

public interface UnitService {
  List<Unit> getAllUnits();
  Unit getUnitByName(String name);
  int createUnit(Unit unit);
  int updateUnit(Unit unit);
  int deleteUnitByName(String name);
}

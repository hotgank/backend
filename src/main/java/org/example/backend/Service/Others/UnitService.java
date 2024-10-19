package org.example.backend.Service.Others;

import java.util.List;
import org.example.backend.Entity.Others.Unit;

public interface UnitService {
  List<Unit> getAllUnits();
  Unit getUnitByName(String name);
  int createUnit(Unit unit);
  int updateUnit(Unit unit);
  int deleteUnitByName(String name);
}

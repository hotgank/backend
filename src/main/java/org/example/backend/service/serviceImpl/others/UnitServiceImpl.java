package org.example.backend.service.serviceImpl.others;

import java.util.List;
import org.example.backend.entity.others.Unit;
import org.example.backend.mapper.others.UnitMapper;
import org.example.backend.service.others.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl implements UnitService {
  @Autowired
  private UnitMapper unitMapper;
  @Override
public List<Unit> getAllUnits() {
    return unitMapper.selectList(null);
  }

  @Override
  public Unit getUnitByName(String name) {
    return unitMapper.selectById(name);
  }

  @Override
  public int createUnit(Unit unit) {
    return unitMapper.insert(unit);
  }

  @Override
  public int updateUnit(Unit unit) {
    return unitMapper.updateById(unit);
  }

  @Override
  public int deleteUnitByName(String name) {
    return unitMapper.deleteById(name);
  }
}

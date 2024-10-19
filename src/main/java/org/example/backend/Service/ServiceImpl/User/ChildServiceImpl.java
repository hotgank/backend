package org.example.backend.Service.ServiceImpl.User;

import org.example.backend.Entity.User.Child;
import org.example.backend.Mapper.User.ChildMapper;
import org.example.backend.Service.User.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildServiceImpl implements ChildService {

  @Autowired
  private ChildMapper childMapper;

  @Override
  public Child getById(String childId) {
    return childMapper.selectById(childId);
  }

  @Override
  public List<Child> getAll() {
    return childMapper.selectList(null);
  }

  @Override
  public boolean createChild(Child child) {
    return childMapper.insert(child) > 0;
  }

  @Override
  public boolean updateChild(Child child) {
    return childMapper.updateById(child) > 0;
  }

  @Override
  public boolean deleteChild(String childId) {
    return childMapper.deleteById(childId) > 0;
  }
}

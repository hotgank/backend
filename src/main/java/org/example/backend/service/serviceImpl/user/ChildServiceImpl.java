package org.example.backend.service.serviceImpl.user;

import java.util.UUID;
import org.example.backend.entity.user.Child;
import org.example.backend.mapper.user.ChildMapper;
import org.example.backend.service.user.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildServiceImpl implements ChildService {

    @Autowired
    private ChildMapper childMapper;

    @Override
    public Child selectById(String childId) {
      return childMapper.selectById(childId);
    }

    @Override
    public List<Child> selectAll() {
      return childMapper.selectAll();
    }

    @Override
    public String insertChild(Child child) {
      try {
        String childId = "C-" + UUID.randomUUID();;
        child.setChildId(childId);
        childMapper.insertChild(child);
        return childId;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    public boolean updateChild(Child child) {
      try {
        childMapper.updateChild(child);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }

    @Override
    public boolean deleteChild(String childId) {
      try {
        childMapper.deleteChild(childId);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
}

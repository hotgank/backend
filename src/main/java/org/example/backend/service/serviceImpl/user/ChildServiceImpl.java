package org.example.backend.service.serviceImpl.user;

import java.util.Collections;
import java.util.UUID;
import org.example.backend.entity.user.Child;
import org.example.backend.mapper.user.ChildMapper;
import org.example.backend.service.user.ChildService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildServiceImpl implements ChildService {

  private static final Logger logger = LoggerFactory.getLogger(ChildServiceImpl.class);

  @Autowired private ChildMapper childMapper;

  @Override
  public Child selectById(String childId) {
    try {
      return childMapper.selectById(childId);
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取孩子信息失败，childId: {}", childId, e);
      return null;
    }
  }

  @Override
  public List<Child> selectAll() {
    try {
      return childMapper.selectAll();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取所有孩子失败", e);
      return Collections.emptyList();
    }
  }

  @Override
  public String insert(Child child) {
    try {
      String childId = "C-" + UUID.randomUUID();
      child.setChildId(childId);
      childMapper.insertChild(child);
      logger.info("Child with ID {} inserted successfully", child.getChildId());
      return childId;
    } catch (Exception e) {
      logger.error("Error inserting child with ID {}: {}", child.getChildId(), e.getMessage(), e);
      return null;
    }
  }

  @Override
  public boolean update(Child child) {
    try {
      childMapper.updateChild(child);
      logger.info("Child with ID {} updated successfully", child.getChildId());
      return true;
    } catch (Exception e) {
      logger.error("Error updating child with ID {}: {}", child.getChildId(), e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean delete(String childId) {
    try {
      childMapper.deleteChild(childId);
      logger.info("Child with ID {} deleted successfully", childId);
      return true;
    } catch (Exception e) {
      logger.error("Error deleting child with ID {}: {}", childId, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean insertAllChildren(List<Child> children) {
    try {
      for (Child child : children) {
        insert(child);
      }
      return true;
    } catch (Exception e) {
      logger.error("Error inserting children: {}", e.getMessage(), e);
      return false;
    }
  }
}

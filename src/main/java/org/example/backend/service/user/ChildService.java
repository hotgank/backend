package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.Child;

public interface ChildService {
  Child selectById(String childId);
  List<Child> selectAll();
  String insert(Child child);
  boolean update(Child child);
  boolean delete(String childId);
}

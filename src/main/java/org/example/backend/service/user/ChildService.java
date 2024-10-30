package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.Child;

public interface ChildService {
  Child selectById(String childId);
  List<Child> selectAll();
  boolean insertChild(Child child);
  boolean updateChild(Child child);
  boolean deleteChild(String childId);
}

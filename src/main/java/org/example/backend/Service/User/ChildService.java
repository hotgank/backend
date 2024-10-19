package org.example.backend.Service.User;

import java.util.List;
import org.example.backend.Entity.User.Child;

public interface ChildService {
  Child getById(String childId);
  List<Child> getAll();
  boolean createChild(Child child);
  boolean updateChild(Child child);
  boolean deleteChild(String childId);
}

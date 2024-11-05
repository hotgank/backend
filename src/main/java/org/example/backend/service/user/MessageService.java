package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.Message;

public interface MessageService {
  List<Message> selectMessagesById(String doctorId, String userId);
}

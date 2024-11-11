package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.Message;

public interface MessageService {
  List<Message> selectMessagesById(String doctorId, String userId);

    int insertMessage(Message message);
}

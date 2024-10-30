package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.Message;

public interface MessageService {
  List<Message> getAllMessages();
  Message getMessageById(int id);
  int createMessage(Message message);
  int updateMessage(Message message);
  int deleteMessageById(int id);
}

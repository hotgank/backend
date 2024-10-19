package org.example.backend.Service.User;

import java.util.List;
import org.example.backend.Entity.User.Message;

public interface MessageService {
  List<Message> getAllMessages();
  Message getMessageById(int id);
  int createMessage(Message message);
  int updateMessage(Message message);
  int deleteMessageById(int id);
}

package org.example.backend.Service.ServiceImpl.User;

import java.util.List;
import org.example.backend.Entity.User.Message;
import org.example.backend.Mapper.User.MessageMapper;
import org.example.backend.Service.User.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
  @Autowired
  private MessageMapper messageMapper;

  @Override
  public List<Message> getAllMessages() {
    return messageMapper.selectList(null);
  }

  @Override
  public Message getMessageById(int id) {
    return messageMapper.selectById(id);
  }

  @Override
  public int createMessage(Message message) {
    return messageMapper.insert(message);
  }

  @Override
  public int updateMessage(Message message) {
    return messageMapper.updateById(message);
  }

  @Override
  public int deleteMessageById(int id) {
    return messageMapper.deleteById(id);
  }
}

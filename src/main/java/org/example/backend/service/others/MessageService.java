package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.Message;

public interface MessageService {
  List<Message> selectMessagesById(String doctorId, String userId);

    int insertMessage(Message message);


    public Message getLastMessage( Integer relationId);

    public List<Message> getLast30Messages(Integer relationId);

    public List<Message> getMessagesAfterSeq(Integer relationId, Integer messageSeq);

    public List<Message> getMessagesBeforeSeq(Integer relationId, Integer messageSeq);

    public Message sendMessage(Integer relationId, String senderType, String messageText, String messageType, String url);

  public int countUnreadMessages(int relationId, int readSeg, String senderType);
}

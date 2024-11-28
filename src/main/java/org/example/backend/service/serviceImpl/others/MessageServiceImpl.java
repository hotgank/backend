package org.example.backend.service.serviceImpl.others;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.example.backend.entity.others.Consultation;
import org.example.backend.entity.others.Message;
import org.example.backend.mapper.others.MessageMapper;
import org.example.backend.service.others.ConsultationService;
import org.example.backend.service.others.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

  private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

  @Autowired
  private MessageMapper messageMapper;

  @Autowired
  private ConsultationService consultationService;



  public List<Message> selectMessagesById(String doctorId, String userId) {
//    try {
//      Consultation consultation = consultationService.selectConsultationByDoctorIdAndUserId(doctorId, userId);
//      Integer consultationId = consultation.getConsultationId();
//      return messageMapper.selectById(consultationId);
//    } catch (Exception e) {
//      // 记录异常日志
//      logger.error("获取咨询信息失败, doctorId: {}, userId: {}", doctorId, userId, e);
//      return Collections.emptyList();
//    }
    return null;
  }

  @Override
  public Message getLastMessage( Integer relationId) {
    return messageMapper.getLastMessage(relationId);
  }
  @Override
  public int insertMessage(Message message) {
    return messageMapper.insert(message);
  }



    public List<Message> getLast30Messages(Integer relationId) {
        return messageMapper.findLast30Messages(relationId);
    }

    public List<Message> getMessagesAfterSeq(Integer relationId, Integer messageSeq) {
        return messageMapper.findMessagesAfterSeq(relationId, messageSeq);
    }

    public List<Message> getMessagesBeforeSeq(Integer relationId, Integer messageSeq) {
        return messageMapper.findMessagesBeforeSeq(relationId, messageSeq);
    }

    public Message sendMessage(Integer relationId, String senderType, String messageText, String messageType, String url) {
        Message message = new Message();
        message.setRelationId(relationId);
        message.setSenderType(senderType);
        message.setMessageText(messageText);
        message.setTimestamp(LocalDateTime.now());
        message.setMessageType(messageType);
        message.setUrl(url);

        // 插入消息
        int rowsInserted = messageMapper.insertMessage(message);
        if (rowsInserted > 0) {
            message.setMessageSeq(messageMapper.getLastMessage(relationId).getMessageSeq());
            return message;
        } else {
            throw new RuntimeException("Failed to send message.");
        }
    }

  @Override
  public int countUnreadMessages(int relationId, int readSeg, String senderType) {
    return messageMapper.countUnreadMessages(relationId, readSeg, senderType);
  }
}

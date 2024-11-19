package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.Message;

public interface MessageService {
//  List<Message> selectMessagesById(String doctorId, String userId);
//
//    int insertMessage(Message message);
//
//

    /**
     * 获取最后 30 条消息
     * @param relationId 医生-用户关系的唯一标识
     * @return 消息列表
     */
    public List<Message> getLast30Messages(Integer relationId);

    /**
     * 获取某条消息序号之后的所有消息
     * @param relationId 医生-用户关系的唯一标识
     * @param lastSeq 最后获取到的消息序号
     * @return 消息列表
     */
    public List<Message> getMessagesAfter(Integer relationId, Integer lastSeq);

    /**
     * 获取某条消息序号之前的 15 条消息
     * @param relationId 医生-用户关系的唯一标识
     * @param firstSeq 最早的消息序号
     * @return 消息列表
     */
    public List<Message> getMessagesBefore(Integer relationId, Integer firstSeq);
}

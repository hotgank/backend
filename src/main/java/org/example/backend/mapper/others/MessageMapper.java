package org.example.backend.mapper.others;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.example.backend.entity.others.Message;

@Mapper
public interface MessageMapper {
//  @Select("SELECT * FROM c_messages WHERE consultation_id = #{consultationId} ORDER BY timestamp DESC")
//  @Results({
//      @Result(column = "message_id", property = "messageId"),
//      @Result(column = "consultation_id", property = "consultationId"),
//      @Result(column = "sender_type", property = "senderType"),
//      @Result(column = "message_text", property = "messageText"),
//      @Result(column = "timestamp", property = "timestamp"),
//      @Result(column = "message_type", property = "messageType"),
//      @Result(column = "url", property = "url"),
//  })
//  List <Message> selectById(@Param("consultationId") Integer consultationId);
//
//  @Insert("INSERT INTO c_messages (consultation_id, sender_type, message_text, timestamp, message_type, url)"
//      + "VALUES (#{consultationId}, #{senderType}, #{messageText}, #{timestamp}, #{messageType}, #{url})")
//  @Options(useGeneratedKeys = true, keyProperty = "messageId")
//  int insert(Message message);

      /**
     * 根据关系 ID 获取最后 30 条消息
     * @param relationId 医生-用户关系的唯一标识
     * @return 消息列表，最多包含 30 条
     */
    @Select("SELECT * FROM c_messages WHERE relation_id = #{relationId} ORDER BY message_seq DESC LIMIT 30")
    List<Message> findLast30Messages(@Param("relationId") Integer relationId);

    /**
     * 根据关系 ID 和最后一条消息序号，获取其之后的所有消息
     * @param relationId 医生-用户关系的唯一标识
     * @param lastSeq 最后获取到的消息序号
     * @return 消息列表
     */
    @Select("SELECT * FROM c_messages WHERE relation_id = #{relationId} AND message_seq > #{lastSeq} ORDER BY message_seq ASC")
    List<Message> findMessagesAfter(@Param("relationId") Integer relationId, @Param("lastSeq") Integer lastSeq);

    /**
     * 根据关系 ID 和最早一条消息序号，获取其之前的 15 条消息
     * @param relationId 医生-用户关系的唯一标识
     * @param firstSeq 最早的消息序号
     * @return 消息列表，最多包含 15 条
     */
    @Select("SELECT * FROM c_messages WHERE relation_id = #{relationId} AND message_seq < #{firstSeq} ORDER BY message_seq DESC LIMIT 15")
    List<Message> findMessagesBefore(@Param("relationId") Integer relationId, @Param("firstSeq") Integer firstSeq);


}
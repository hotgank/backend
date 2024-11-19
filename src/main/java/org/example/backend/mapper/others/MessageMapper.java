package org.example.backend.mapper.others;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.example.backend.entity.others.Message;

@Mapper
public interface MessageMapper {
  @Select("SELECT * FROM c_messages WHERE relation_id = #{relationId} ORDER BY message_seq DESC")
  @Results({
      @Result(column = "message_id", property = "messageId"),
      @Result(column = "relation_id", property = "relationId"),
      @Result(column = "message_seq", property = "messageSeq"),
      @Result(column = "sender_type", property = "senderType"),
      @Result(column = "message_text", property = "messageText"),
      @Result(column = "timestamp", property = "timestamp"),
      @Result(column = "message_type", property = "messageType"),
      @Result(column = "url", property = "url"),
  })
  List<Message> selectByRelationId(@Param("relationId") Integer relationId);

  @Insert("INSERT INTO c_messages (relation_id, message_seq, sender_type, message_text, timestamp, message_type, url)"
      + " VALUES (#{relationId}, #{messageSeq}, #{senderType}, #{messageText}, #{timestamp}, #{messageType}, #{url})")
  @Options(useGeneratedKeys = true, keyProperty = "messageId")
  int insert(Message message);

    // 根据关系ID查询最后30条消息
    @Select("""
        SELECT * FROM c_messages 
        WHERE relation_id = #{relationId} 
        ORDER BY message_seq DESC 
        LIMIT 30
    """)
    @Results({
        @Result(column = "message_id", property = "messageId"),
        @Result(column = "relation_id", property = "relationId"),
        @Result(column = "message_seq", property = "messageSeq"),
        @Result(column = "sender_type", property = "senderType"),
        @Result(column = "message_text", property = "messageText"),
        @Result(column = "timestamp", property = "timestamp"),
        @Result(column = "message_type", property ="messageType"),
    })
    List<Message> findLast30Messages(@Param("relationId") Integer relationId);

    // 根据关系ID和最新消息序号查询之后的消息
    @Select("""
        SELECT * FROM c_messages 
        WHERE relation_id = #{relationId} 
          AND message_seq > #{messageSeq} 
        ORDER BY message_seq ASC
    """)
    @Results({
        @Result(column = "message_id", property = "messageId"),
        @Result(column = "relation_id", property = "relationId"),
        @Result(column = "message_seq", property = "messageSeq"),
        @Result(column = "sender_type", property = "senderType"),
        @Result(column = "message_text", property = "messageText"),
        @Result(column = "timestamp", property = "timestamp"),
        @Result(column = "message_type", property ="messageType"),
    })
    List<Message> findMessagesAfterSeq(@Param("relationId") Integer relationId, @Param("messageSeq") Integer messageSeq);

    // 根据关系ID和最早消息序号查询之前的消息
    @Select("""
        SELECT * FROM c_messages 
        WHERE relation_id = #{relationId} 
          AND message_seq < #{messageSeq} 
        ORDER BY message_seq DESC 
        LIMIT 15
    """)
    @Results({
        @Result(column = "message_id", property = "messageId"),
        @Result(column = "relation_id", property = "relationId"),
        @Result(column = "message_seq", property = "messageSeq"),
        @Result(column = "sender_type", property = "senderType"),
        @Result(column = "message_text", property = "messageText"),
        @Result(column = "timestamp", property = "timestamp"),
        @Result(column = "message_type", property ="messageType"),
    })
    List<Message> findMessagesBeforeSeq(@Param("relationId") Integer relationId, @Param("messageSeq") Integer messageSeq);

    // 插入新消息
    @Insert("""
        INSERT INTO c_messages (relation_id, sender_type, message_text, timestamp, message_type, url)
        VALUES (#{relationId}, #{senderType}, #{messageText}, #{timestamp}, #{messageType}, #{url})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "messageSeq")
    int insertMessage(Message message);


}
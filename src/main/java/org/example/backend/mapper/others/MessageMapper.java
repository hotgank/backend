package org.example.backend.mapper.others;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.example.backend.entity.others.Message;

@Mapper
public interface MessageMapper {
  @Select("SELECT * FROM c_messages WHERE consultation_id = #{consultationId} ORDER BY timestamp DESC")
  @Results({
      @Result(column = "message_id", property = "messageId"),
      @Result(column = "consultation_id", property = "consultationId"),
      @Result(column = "sender_type", property = "senderType"),
      @Result(column = "message_text", property = "messageText"),
      @Result(column = "timestamp", property = "timestamp"),
      @Result(column = "message_type", property = "messageType"),
      @Result(column = "url", property = "url"),
  })
  List <Message> selectById(@Param("consultationId") Integer consultationId);

  @Insert("INSERT INTO c_messages (consultation_id, sender_type, message_text, timestamp, message_type, url)"
      + "VALUES (#{consultationId}, #{senderType}, #{messageText}, #{timestamp}, #{messageType}, #{url})")
  @Options(useGeneratedKeys = true, keyProperty = "messageId")
  int insert(Message message);
}
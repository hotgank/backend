package org.example.backend.mapper.user;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.user.Message;

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

}
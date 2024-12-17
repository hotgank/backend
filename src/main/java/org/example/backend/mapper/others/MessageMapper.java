package org.example.backend.mapper.others;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.example.backend.entity.others.Message;

@Mapper
public interface MessageMapper {

  @Select(
      """
        SELECT * FROM c_messages
        WHERE relation_id = #{relationId}
        ORDER BY message_seq DESC
        LIMIT 1
    """)
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
  Message getLastMessage(@Param("relationId") Integer relationId);

  @Select("SELECT * FROM c_messages WHERE relation_id = #{relationId} ORDER BY message_seq ")
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

  @Insert(
      "INSERT INTO c_messages (relation_id, message_seq, sender_type, message_text, timestamp, message_type, url)"
          + " VALUES (#{relationId}, #{messageSeq}, #{senderType}, #{messageText}, #{timestamp}, #{messageType}, #{url})")
  @Options(useGeneratedKeys = true, keyProperty = "messageId")
  int insert(Message message);

  @Select(
      """
        SELECT * FROM (
          SELECT * FROM c_messages
          WHERE relation_id = #{relationId}
          ORDER BY message_seq DESC
          LIMIT 30
        ) AS last_30_messages
        ORDER BY message_seq
      """)
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
  List<Message> getNew30Messages(@Param("relationId") Integer relationId);

  // 根据关系ID查询最后30条消息
  @Select(
          """
            SELECT *
            FROM (
                SELECT *
                FROM c_messages
                WHERE relation_id = #{relationId}
                ORDER BY message_seq DESC
                LIMIT 30
            ) AS latest_messages
            ORDER BY message_seq
          """)
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
  List<Message> findLast30Messages(@Param("relationId") Integer relationId);

  // 根据关系ID和最新消息序号查询之后的消息
  @Select(
      """
        SELECT * FROM c_messages
        WHERE relation_id = #{relationId}
          AND message_seq > #{messageSeq}
        ORDER BY message_seq
    """)
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
  List<Message> findMessagesAfterSeq(
      @Param("relationId") Integer relationId, @Param("messageSeq") Integer messageSeq);

  // 根据关系ID和最早消息序号查询之前的消息
  @Select(
      """
        SELECT * FROM c_messages
        WHERE relation_id = #{relationId}
          AND message_seq < #{messageSeq}
        ORDER BY message_seq
        LIMIT 15
    """)
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
  List<Message> findMessagesBeforeSeq(
      @Param("relationId") Integer relationId, @Param("messageSeq") Integer messageSeq);

  // 插入新消息
  @Insert(
      """
        INSERT INTO c_messages (relation_id, sender_type, message_text, timestamp, message_type, url)
        VALUES (#{relationId}, #{senderType}, #{messageText}, #{timestamp}, #{messageType}, #{url})
    """)
  @Options(useGeneratedKeys = true, keyProperty = "messageSeq")
  int insertMessage(Message message);

  // 查询未读消息数量
  @Select(
      "SELECT COUNT(*) "
          + "FROM c_messages "
          + "WHERE relation_id = #{relationId} "
          + "AND message_seq > #{readSeg} "
          + "AND sender_type = #{senderType} ")
  int countUnreadMessages(
      @Param("relationId") int relationId,
      @Param("readSeg") int readSeg,
      @Param("senderType") String senderType);

  // 查询今天咨询的用户数量
  @Select(
      """
      SELECT
          COUNT(DISTINCT cm.relation_id) AS consultation_count
      FROM
          c_messages cm
      JOIN
          d_doctors_users ddu ON cm.relation_id = ddu.relation_id
      JOIN
          d_doctors d ON ddu.doctor_id = d.doctor_id
      WHERE
          d.doctor_id = #{doctorId}
          AND cm.sender_type = 'user'
          AND DATE(cm.timestamp) = CURDATE()
      """
  )
  int TodayConsultationUserCount(@Param("doctorId") String doctorId);


}

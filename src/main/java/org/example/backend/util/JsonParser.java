package org.example.backend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class JsonParser {

  private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

  /**
   * 解析JSON字符串并获取指定字段的值。
   *
   * @param jsonString JSON格式的字符串
   * @param fieldName 要获取的字段名称
   * @return 字段对应的值，如果字段不存在则返回null
   */
  public String parseJsonString(String jsonString, String fieldName) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonString);
      JsonNode fieldNode = jsonNode.get(fieldName);
      if (fieldNode != null && !fieldNode.isNull()) {
        return fieldNode.asText();
      }
    } catch (Exception e) {
      // 处理异常，如JSON格式错误或字段不存在
      System.out.println("Error parsing JSON: " + e.getMessage());
    }
    return null;
  }

  /**
   * 解析JSON字符串中的指定字段并将其转换为整数
   * 此方法主要用于从给定的JSON字符串中提取指定字段的值，并将该值转换为整数类型
   * 如果字段不存在、JSON格式有误或转换失败，方法将返回0
   *
   * @param jsonString 待解析的JSON字符串
   * @param fieldName 要提取并转换为整数的字段名称
   * @return 字段对应的整数值，如果操作失败则返回0
   */
  public int parseJsonInt(String jsonString, String fieldName) {
    try {
      // 创建ObjectMapper实例以解析JSON
      ObjectMapper objectMapper = new ObjectMapper();
      // 解析JSON字符串为JsonNode对象
      JsonNode jsonNode = objectMapper.readTree(jsonString);
      // 获取指定字段的JsonNode
      JsonNode fieldNode = jsonNode.get(fieldName);
      // 检查字段是否存在且不为null
      if (fieldNode != null && !fieldNode.isNull()) {
        // 将字段值转换为整数并返回
        return fieldNode.asInt();
      }
    } catch (Exception e) {
      // 处理异常，如JSON格式错误或字段不存在
      System.out.println("Error parsing JSON: " + e.getMessage());
    }
    // 如果解析失败或字段不存在，返回0
    return 0;
  }

  /**
   * 将单个实体的toString()输出转换为JSON
   * 此方法使用Jackson库将实体对象转换为格式化的JSON字符串
   * 主要用于调试和日志记录，以便开发者更直观地查看对象结构
   *
   * @param entity 待转换的实体对象
   * @return 转换后的JSON字符串，如果转换过程中发生异常，则返回错误信息
   */
  public String toJsonFromEntity(Object entity) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      // 将对象转换为JSON字符串
      return objectMapper.writeValueAsString(entity);
    } catch (Exception e) {
      logger.error("转换失败", e);
      return "转换失败: " + e.getMessage();
    }
  }

  /**
   * 将List<Entity>转换为JSON列表
   * 此方法使用Jackson库将实体对象列表转换为JSON格式字符串
   * 它通常用于数据交换或前端展示
   *
   * @param entityList 实体对象列表，可以是任意类型的List
   * @return 返回转换后的JSON字符串如果转换过程中发生异常，则返回错误信息
   */
  public String toJsonFromEntityList(List<?> entityList) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());  // 注册 JavaTimeModule
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      // 将对象列表转换为JSON字符串
      return objectMapper.writeValueAsString(entityList);
    } catch (Exception e) {
      logger.error("转换失败", e);
      return "转换失败: " + e.getMessage();
    }
  }

  /**
   * 从JSON对象中移除指定的键值对
   *
   * @param jsonString JSON格式的字符串
   * @param key 要移除的键
   * @return 移除指定键值对后的JSON字符串
   */
  public String removeKeyFromJson(String jsonString, String key) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(jsonString);

      // 递归移除键值对
      removeKeyRecursively(rootNode, key);

      return objectMapper.writeValueAsString(rootNode);
    } catch (IOException e) {
        logger.info("移除键值对失败: {}", e.getMessage());
      return "移除键值对失败: " + e.getMessage();
    }
  }

  private static void removeKeyRecursively(JsonNode node, String key) {
    if (node instanceof ObjectNode) {
      ((ObjectNode) node).remove(key);
      for (Iterator<String> it = node.fieldNames(); it.hasNext(); ) {
        String fieldName = it.next();
        removeKeyRecursively(node.get(fieldName), key);
      }
    } else if (node instanceof ArrayNode) {
      for (JsonNode element : node) {
        removeKeyRecursively(element, key);
      }
    }
  }
}

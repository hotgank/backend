package org.example.backend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

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
}

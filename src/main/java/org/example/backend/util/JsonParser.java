package org.example.backend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

  /**
   * 解析JSON字符串并获取指定字段的值。
   *
   * @param jsonString JSON格式的字符串
   * @param fieldName 要获取的字段名称
   * @return 字段对应的值，如果字段不存在则返回null
   */
  public static String parseJsonString(String jsonString, String fieldName) {
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

}

package org.example.backend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.example.backend.entity.others.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReportUtil{

  private static final Logger logger = LoggerFactory.getLogger(ReportUtil.class);

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * 从 JSON 字符串生成报告对象
   * 此方法解析给定的 JSON 字符串，以提取与报告类型匹配的信息，并将结果填充到报告对象中
   *
   * @param jsonResponse 包含人体体态检测信息的 JSON 响应字符串
   * @param report 待填充的报告对象，包含报告类型等信息
   * @return 填充了从 JSON 响应中提取的信息后的报告对象
   */
  public Report generateReportFromJson(String jsonResponse, Report report) {
    try {
      // 解析 JSON 字符串为 JsonNode 对象
      JsonNode rootNode = objectMapper.readTree(jsonResponse);
      JsonNode symptomsArray = rootNode.path("人体体态检测实体信息");

      // 遍历 JSON 数组，查找指定的 reportType
      for (JsonNode symptomNode : symptomsArray) {
        if (report.getReportType().equals(symptomNode.path("人体体态检测指标").asText())) {
          String result = symptomNode.path("人体体态检测指标数值").asText();
          report.setResult(result);
          String analysis = getScoliosisRecommendation(result);
          report.setAnalyse(analysis);
          break;
        }
      }
      // 如果找不到对应的症状
      if (report.getResult() == null) {
        report.setResult("未检测到相关症状");
      }
    } catch (IOException e) {
      logger.error("解析症状失败", e);
    }
    return report;
  }
  public static String getScoliosisRecommendation(String detectionResult) {
    return switch (detectionResult) {
      case "正常" ->
          "您的脊柱状态良好，无需特殊治疗。建议保持健康的坐姿与日常锻炼以维持脊柱健康。";
      case "轻度脊柱异位" ->
          "您的脊柱有轻微偏移。建议进行轻度的姿势矫正练习和背部肌肉加强锻炼，以防止进一步恶化。";
      case "中度脊柱异位" ->
          "您的脊柱已经出现明显的偏移。建议咨询物理治疗师进行定期的脊柱矫正训练，配合背部拉伸与矫正运动。";
      case "重度脊柱异位" ->
          "脊柱偏移较为严重，可能引发腰部疼痛和长短腿。建议立即寻求专业医生的帮助，进行详细的检查和治疗，可能需要物理治疗或支具。";
      case "严重脊柱异位" ->
          "您的脊柱有严重的偏移，已严重影响身体结构。建议尽快与骨科专家会诊，可能需要进行矫正手术或长期物理治疗以恢复脊柱的正常功能。";
      default -> "未知结果。";
    };
  }
}


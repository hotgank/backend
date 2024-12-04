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
          String analysis = null;
          switch (report.getReportType()) {
            case "头部侧倾" -> analysis = getHeadTiltRecommendation(result);
            case "高低肩" -> analysis = getShoulderImbalanceRecommendation(result);
            case "骨盆侧倾" -> analysis = getPelvicTiltRecommendation(result);
            case "长短腿" -> analysis = getLegLengthDiscrepancyRecommendation(result);
            case "脊柱异位" -> analysis = getScoliosisRecommendation(result);
            case "X/O/XO型腿" -> analysis = getLegShapeRecommendation(result);
          }
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

  /**
   * 获取头部侧倾检测分析
   *
   * @param detectionResult 检测结果
   * @return recommendations
   */
  public static String getHeadTiltRecommendation(String detectionResult) {
    return switch (detectionResult) {
      case "正常" -> "您的头部状态良好，无需担心。保持健康的坐姿和站姿，避免长时间低头。";
      case "轻度头部倾侧" -> "您的头部有轻微偏斜，建议注意姿势，避免单侧使用手机或支撑头部。";
      case "中度头部倾侧" -> "您的头部偏斜较明显，建议进行颈部拉伸和调整练习，必要时咨询物理治疗师。";
      case "重度头部倾侧" -> "您的头部偏斜严重，可能引发颈椎问题，建议寻求专业医生的帮助进行治疗。";
      case "严重头部倾侧" -> "您的头部倾斜已非常严重，可能影响生活质量，请立即咨询骨科专家。";
      default -> "未知检测结果。";
    };
  }

  /**
   * 获取高低肩检测分析
   *
   * @param detectionResult 检测结果
   * @return recommendations
   */
  public static String getShoulderImbalanceRecommendation(String detectionResult) {
    return switch (detectionResult) {
      case "正常" -> "您的肩部状态良好，请保持正确的站姿和坐姿，避免长时间单肩用力。";
      case "轻度高低肩" -> "您的肩部略有不平，建议进行肩部对称性拉伸练习。";
      case "中度高低肩" -> "您的肩部不平较明显，建议进行专业康复训练，改善肩部姿势。";
      case "重度高低肩" -> "您的高低肩问题较严重，可能影响脊柱健康，建议寻求物理治疗帮助。";
      case "严重高低肩" -> "您的高低肩情况非常严重，可能需要矫正支具或进一步医疗干预，请立即就医。";
      default -> "未知检测结果。";
    };
  }

  /**
   * 获取骨盆侧倾检测分析
   *
   * @param detectionResult 检测结果
   * @return recommendations
   */
  public static String getPelvicTiltRecommendation(String detectionResult) {
    return switch (detectionResult) {
      case "正常" -> "您的骨盆状态正常，请保持正确姿势，避免长时间久坐。";
      case "轻度骨盆侧倾" -> "您的骨盆有轻微侧倾，建议进行臀部拉伸和核心肌肉训练。";
      case "中度骨盆侧倾" -> "您的骨盆侧倾较明显，建议进行针对性的体态矫正练习。";
      case "重度骨盆侧倾" -> "您的骨盆侧倾严重，可能导致腰椎问题，建议咨询专业医生进行治疗。";
      case "严重骨盆侧倾" -> "您的骨盆侧倾已严重影响生活，建议立即就医并接受专业治疗。";
      default -> "未知检测结果。";
    };
  }

  /**
   * 获取长短腿检测分析
   *
   * @param detectionResult 检测结果
   * @return recommendations
   */
  public static String getLegLengthDiscrepancyRecommendation(String detectionResult) {
    return switch (detectionResult) {
      case "正常" -> "您的腿长对称，无需担心，注意保持良好的运动习惯。";
      case "轻度长短腿" -> "您的腿长略有不齐，建议选择符合腿长的鞋垫或进行适度矫正练习。";
      case "中度长短腿" -> "您的腿长不齐较明显，可能需要佩戴定制鞋垫或咨询医生。";
      case "重度长短腿" -> "您的腿长差异较大，可能引发腰部和关节问题，请咨询专业医生。";
      case "严重长短腿" -> "您的腿长差异已非常严重，建议尽快就医，可能需要手术或特殊治疗。";
      default -> "未知检测结果。";
    };
  }

  /**
   * 获取脊柱异位检测分析
   *
   * @param detectionResult 检测结果
   * @return recommendations
   */
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

  /**
   * 获取腿型检测分析
   *
   * @param detectionResult 检测结果
   * @return recommendations
   */
  public static String getLegShapeRecommendation(String detectionResult) {
    return switch (detectionResult) {
      case "正常腿型" -> "您的腿型正常，无需担心，保持规律运动以维持健康状态。";
      case "轻度O型腿", "轻度X型腿" -> "您的腿型有轻微异常，建议进行腿部拉伸和步态矫正练习。";
      case "中度O型腿", "中度X型腿" -> "您的腿型异常较明显，建议咨询医生进行步态分析和矫正训练。";
      case "重度O型腿", "重度X型腿" -> "您的腿型问题严重，可能需要佩戴矫正支具或进行医疗干预。";
      case "严重O型腿", "严重X型腿", "严重OX型腿" -> "您的腿型异常已严重影响功能，请尽快就医，可能需要手术治疗。";
      default -> "未知检测结果。";
    };
  }


}


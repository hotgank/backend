package org.example.backend.controller.others;

import jakarta.servlet.http.HttpServletRequest;
import javax.management.relation.Relation;
import org.example.backend.entity.doctor.DoctorUserRelation;
import org.example.backend.util.JsonParser;
import org.example.backend.util.MultipartFileUtil;
import org.example.backend.util.UrlUtil;
import org.example.backend.service.doctor.DoctorUserRelationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/url")
public class UrlController {

  @Autowired UrlUtil urlUtil;

  @Autowired private JsonParser jsonParser;

  @Autowired private DoctorUserRelationService doctorUserRelationService;

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UrlController.class);

  @GetMapping("/avatar")
  public ResponseEntity<Resource> getFile(@RequestParam String url) {
    if (!url.startsWith("http://localhost:8080/doctor_avatars")) {
      return ResponseEntity.badRequest().body(null);
    }
    return urlUtil.getFile(url);
  }

  @GetMapping("/base64")
  public ResponseEntity<String> getImageAsBase64(@RequestParam String url) {
    if (!url.startsWith("http://localhost:8080/")) {
      return ResponseEntity.badRequest().body(null);
    } // 使用工具类获取 Base64 编码

    return urlUtil.getImageAsBase64(url);
  }

  @PostMapping("/getReportImage")
  public ResponseEntity<Resource> getReportImage(@RequestBody String urlJson) {
    String url = jsonParser.parseJsonString(urlJson, "url");
    logger.info("getReportImage: " + url);
    return urlUtil.getFile(url);
  }

  @GetMapping("/getLicenseImage")
  public ResponseEntity<Resource> getLicenseImage(@RequestParam String url) {
    System.out.println(url);
    if (!url.startsWith("http://localhost:8080/")) {
      return ResponseEntity.badRequest().body(null);
    }
    logger.info("getLicenseImage: " + url);
    return urlUtil.getFile(url);
  }

  @GetMapping("/getMessageAttachment")
  public ResponseEntity<Resource> getMessageAttachment(
      @RequestParam String url, HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    if (!url.startsWith("http://localhost:8080/MessageFiles")) {
      return ResponseEntity.badRequest().body(null);
    }
    int relationId = extractIntegerFromUrl(url);
    DoctorUserRelation relation = doctorUserRelationService.getRelationById(relationId);
    if (relation == null) {
      return ResponseEntity.badRequest().body(null);
    }
    if (relation.getUserId().equals(userId) || relation.getDoctorId().equals(userId)) {
      return urlUtil.getFile(url);
    }
    return ResponseEntity.status(500).body(null);
  }

  public int extractIntegerFromUrl(String url) {
    String[] parts = url.split("/");
    for (int i = 0; i < parts.length; i++) {
      if ("MessageFiles".equals(parts[i]) && i + 1 < parts.length) {
        try {
          return Integer.parseInt(parts[i + 1]);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid integer in URL");
        }
      }
    }
    throw new IllegalArgumentException("Invalid URL format");
  }
}

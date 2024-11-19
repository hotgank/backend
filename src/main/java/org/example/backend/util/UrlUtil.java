package org.example.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UrlUtil {

  private static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);

  public ResponseEntity<Resource> getFile(String url) {
    // 检查 URL 是否以 localhost:8080 开头
    if (!url.startsWith("http://localhost:8080/")) {
      return ResponseEntity.badRequest().body(null);
    }
    logger.info("Received URL: {}", url);

    // 提取文件路径部分
    String filePath = url.replace("http://localhost:8080/", "");
    logger.info("Extracted file path: {}", filePath);

    // 构建文件路径
    Path path = Paths.get(System.getProperty("user.dir"), "uploads", filePath);
    logger.info("Constructed file path: {}", path);

    // 检查文件是否存在
    if (!Files.exists(path)) {
      logger.warn("File not found at path: {}", path);
      return ResponseEntity.notFound().build();
    }

    // 创建文件资源
    Resource resource = new FileSystemResource(path.toFile());

    // 设置响应头
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

    // 返回文件
    try {
      return ResponseEntity.ok()
              .headers(headers)
              .contentLength(resource.contentLength())
              .contentType(MediaType.APPLICATION_OCTET_STREAM)
              .body(resource);
    } catch (IOException e) {
      logger.error("Error reading file from path: {}", path, e);
      throw new RuntimeException(e);
    }
  }
}

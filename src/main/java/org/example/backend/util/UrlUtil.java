package org.example.backend.util;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
  // 获取图片并转换为 Base64 编码
  public ResponseEntity<String> getImageAsBase64(String url) {
    // 检查 URL 是否以 localhost:8080 开头
    if (!url.startsWith("http://localhost:8080/")) {
        logger.warn("非法 URL: {}", url);
        return ResponseEntity.badRequest().body("Invalid URL");
    }

    logger.info("接收到的 URL: {}", url);

    // 提取文件路径部分
    String filePath = url.replace("http://localhost:8080/", "");
    logger.info("提取的文件路径: {}", filePath);

    // 构建文件路径
    Path path = Paths.get(System.getProperty("user.dir"), "uploads", filePath);
    logger.info("构建的完整路径: {}", path);

    // 检查文件是否存在
    if (!Files.exists(path)) {
        logger.warn("文件未找到: {}", path);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
    }

    try {
        // 读取文件内容为字节数组
        byte[] fileBytes = Files.readAllBytes(path);

        // 检测文件类型，确保是图片
        String mimeType = Files.probeContentType(path);
        if (mimeType == null || !mimeType.startsWith("image/")) {
            logger.warn("文件不是图片: {}", path);
            return ResponseEntity.badRequest().body("File is not an image");
        }

        // 转换为 Base64 编码
        String base64EncodedImage = Base64.encodeBase64String(fileBytes);

        // 返回 Base64 字符串（包含 MIME 类型，适用于直接嵌入 HTML）
        String base64Image = "data:" + mimeType + ";base64," + base64EncodedImage;
        return ResponseEntity.ok(base64Image);

    } catch (IOException e) {
        logger.error("读取文件时发生错误: {}", path, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file");
    }
}
}

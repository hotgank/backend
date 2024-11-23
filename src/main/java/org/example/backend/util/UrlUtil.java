package org.example.backend.util;


import java.nio.file.StandardCopyOption;
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
      logger.warn("Invalid URL: {}", url);
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
    headers.add(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + resource.getFilename() + "\"");

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

   /**
   * 上传资源到指定的基础路径下，并返回资源的 URL。
   *
   * @param resource 要上传的资源
   * @param basePath 基础路径，例如 "/a/b"
   * @return 上传成功后返回资源的 URL，否则返回错误信息
   */
  /**
   * 上传资源到指定的基础路径下，并返回资源的 URL。
   *
   * @param resource 要上传的资源
   * @param basePath 基础路径，例如 "/a/b"
   * @return 上传成功后返回资源的 URL，否则返回错误信息
   */
public String uploadResource(Resource resource, String basePath) {
    // 检查输入参数
    if (resource == null) {
        logger.error("Resource is null");
        return "Resource is null";
    }

    if (basePath == null || basePath.isEmpty()) {
        logger.error("Base path is null or empty");
        return "Base path is null or empty";
    }

    // 构建目标文件路径，包括基路径和上传目录
    Path targetPath = Paths.get(System.getProperty("user.dir"), "uploads", basePath);
    logger.info("Target path: {}", targetPath);

    // 确保目标目录存在
    if (!Files.exists(targetPath)) {
        try {
            Files.createDirectories(targetPath);
        } catch (IOException e) {
            logger.error("Failed to create directories: {}", targetPath, e);
            return "Failed to create directories";
        }
    }

    // 获取资源的文件名
    String filename = resource.getFilename();
    if (filename == null || filename.isEmpty()) {
        logger.error("Resource filename is null or empty");
        return "Resource filename is null or empty";
    }

    // 构建目标文件的完整路径
    Path targetFile = targetPath.resolve(filename);
    logger.info("Target file: {}", targetFile);

    // 将资源保存到目标文件
    try {
        Files.copy(resource.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
        logger.error("Failed to save file: {}", targetFile, e);
        return "Failed to save file";
    }

    // 构建返回的资源URL路径
    String resourceUrl = "http://localhost:8080/" + basePath + "/" + filename;
    logger.info("Resource URL: {}", resourceUrl);

    return resourceUrl;
}


}

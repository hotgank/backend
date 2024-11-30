package org.example.backend.controller.others;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/image")
public class ImageController {

  private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

 @PostMapping("/uploadChatImage/{relationId}")
public ResponseEntity<String> uploadChatImage(
        @PathVariable("relationId") int relationId, // 从路径中获取 int 类型的 relationId
        @RequestParam("file") MultipartFile file) {

    // 检查 relationId 是否为有效值（可根据业务逻辑调整，例如不能小于等于 0）
    if (relationId <= 0) {
        return ResponseEntity.badRequest().body("Invalid Relation ID");
    }

    // 定义上传文件夹路径：upload/chat/{relationId}
    String folder = System.getProperty("user.dir") + File.separator + "uploads"
                    + File.separator + "MessageFiles" + File.separator + relationId + File.separator;

    // 获取原始文件名并清理文件名
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    if (fileName == null || fileName.isEmpty()) {
        return ResponseEntity.badRequest().body("File name is missing");
    }

    // 检查文件名是否包含不安全的路径
    if (fileName.contains("..")) {
        return ResponseEntity.badRequest().body("File name contains invalid path sequence");
    }

    // 检查文件类型是否为图片
    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("image/")) {
        return ResponseEntity.badRequest().body("Only image files are allowed");
    }

    try {
        // 确保文件夹存在
        File directory = new File(folder);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 生成唯一文件名
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        // 文件完整保存路径
        Path path = Paths.get(folder + uniqueFileName);

        // 保存文件到指定路径
        byte[] bytes = file.getBytes();
        Files.write(path, bytes);

        // 生成图片的访问 URL
        String serverUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                              .path("/MessageFiles/" + relationId + "/")
                              .toUriString();
        String imageUrl = serverUrl + uniqueFileName;

        // 返回图片 URL
        return ResponseEntity.ok("{\"imageUrl\":\"" + imageUrl + "\"}");
    } catch (IOException e) {
        // 处理文件保存异常
        logger.error("Error occurred while uploading image: {}", fileName, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error occurred while uploading image: " + e.getMessage());
    }
}

  // 上传图片并返回图片的URL
  @PostMapping("/upload")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    // 构建一个文件夹路径字符串，用于保存上传的图片,根目录下的uploads/images文件夹
    String folder = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "images" + File.separator;
    String fileName = file.getOriginalFilename();
    if (fileName == null) {
      return new ResponseEntity<>("File name is missing", HttpStatus.BAD_REQUEST);
    }
    try {
      // 确保文件夹存在
      File directory = new File(folder);
      if (!directory.exists()) {
        directory.mkdirs();
      }

      // 将图片保存到文件夹
      byte[] bytes = file.getBytes();
      Path path = Paths.get(folder + fileName);
      Files.write(path, bytes);

      // 返回图片的URL（假设你的URL是 localhost:8080/images/{filename}）
      //localhost:8080根据实际 IP 和端口号进行修改
      String imageUrl = "http://localhost:8080/images/" + fileName;
      //return new ResponseEntity<>(imageUrl, HttpStatus.OK);
      return ResponseEntity.ok("{\"imageUrl\":\""+imageUrl+"\"}");
    } catch (IOException e) {
      logger.error("Error occurred while uploading image", e);
      return new ResponseEntity<>("Error occurred while uploading image", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // 根据文件名删除图片
  @DeleteMapping("/delete/{filename}")
  public ResponseEntity<String> deleteImage(@PathVariable("filename") String filename) {
    String folder = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "images" + File.separator;
    File file = new File(folder + filename);

    if (file.exists()) {
      if (file.delete()) {
        return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Failed to delete image", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
    }
  }
}

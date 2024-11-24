package org.example.backend.controller.others;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image")
public class ImageController {

  private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

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
    String folder = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "imagea" + File.separator;
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

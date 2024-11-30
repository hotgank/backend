package org.example.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class MultipartFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(MultipartFileUtil.class);

    /**
     * 保存上传的文件到指定的目录
     *
     * @param file 要保存的文件，类型为MultipartFile
     * @param url 文件保存路径的后缀，用于区分不同的上传路径
     * @return 返回保存后的文件路径，如果保存失败则返回null
     */
    public String saveFile(MultipartFile file, String url) {
        try {
            // 指定文件保存路径
            String uploadDir = "uploads/" + url;
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    // 目录创建失败，抛出异常或记录日志
                    throw new IOException("Failed to create directory: " + dir.getAbsolutePath());
                } else {
                    // 记录目录创建成功
                    logger.info("Directory created successfully: {}", dir.getAbsolutePath());
                }
            }

            // 生成UUID作为文件名
            String uuid = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFileName = uuid + fileExtension;

            // 保存文件
            Path filePath = Paths.get(uploadDir + newFileName);
            Files.copy(file.getInputStream(), filePath);
            return filePath.toString();
        } catch (Exception e) {
            logger.error("Error occurred while saving file: {}", e.getMessage(), e);
            return null;
        }
    }

    public String saveMultipartFile(MultipartFile file, String url) {
        try {
            // 指定文件保存路径
            String uploadDir = "uploads/" + url;
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    // 目录创建失败，抛出异常或记录日志
                    throw new IOException("Failed to create directory: " + dir.getAbsolutePath());
                } else {
                    // 记录目录创建成功
                    logger.info("Directory created successfully: {}", dir.getAbsolutePath());
                }
            }

            // 生成UUID作为文件名
            String uuid = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFileName = uuid + fileExtension;

            // 保存文件
            Path filePath = Paths.get(uploadDir + newFileName);
            Files.copy(file.getInputStream(), filePath);
            return "http://localhost:8080/"+url+newFileName;
        } catch (Exception e) {
            logger.error("Error occurred while saving file: {}", e.getMessage(), e);
            return null;
        }
    }

}

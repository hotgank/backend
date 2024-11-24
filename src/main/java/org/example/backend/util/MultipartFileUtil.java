package org.example.backend.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class MultipartFileUtil {
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
                dir.mkdirs();
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
            e.printStackTrace();
            return null;
        }
    }

}

package org.example.backend;

import org.example.backend.util.UrlUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlUtilTest {

  private UrlUtil urlUtil;

  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    urlUtil = new UrlUtil();
  }

@Test
void testUploadResourceSuccess() throws IOException {
byte[] content = "123".getBytes();
Resource resource = new ByteArrayResource(content) {
    @Override
    public String getFilename() {
        return "test.txt";  // 手动返回文件名
    }
};

// 打印文件名
System.out.println("Filename: " + resource.getFilename());

// 检查文件名是否正确
assertEquals("test.txt", resource.getFilename());


    // 设置 base path 为 /a/b
    String basePath = "a/b";
    System.out.println("Base Path: " + basePath);
    // 执行上传方法
    String result = urlUtil.uploadResource(resource, basePath);

    // 验证上传的资源URL是否正确
    assertEquals("http://localhost:8080/a/b/test.txt", result);

    // 可以进一步验证文件是否成功存储在目标目录
    Path targetFile = tempDir.resolve("uploads").resolve(basePath).resolve("test.txt");
    // assertTrue(Files.exists(targetFile));
}



}

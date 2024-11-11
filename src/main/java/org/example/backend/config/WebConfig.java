package org.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  // 配置静态资源映射
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 将 /images/ URL 映射到项目根目录下的 AIDetectionImage 文件夹
    registry.addResourceHandler("/images/**")
        .addResourceLocations("file:" + System.getProperty("user.dir") + "/AIDetectionImage/");
  }
}


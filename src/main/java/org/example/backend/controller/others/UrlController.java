package org.example.backend.controller.others;

import org.example.backend.util.JsonParser;
import org.example.backend.util.UrlUtil;
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

@RestController
@RequestMapping("/api/url")
public class UrlController {

  @Autowired
  UrlUtil urlUtil;

  @Autowired
  private JsonParser jsonParser;

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
        }
        return urlUtil.getImageAsBase64(url);
    }

    @PostMapping("/getReportImage")
    public ResponseEntity <Resource> getReportImage(@RequestBody String urlJson) {
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
}

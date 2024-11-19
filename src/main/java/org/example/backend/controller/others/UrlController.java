package org.example.backend.controller.others;

import org.example.backend.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url")
public class UrlController {

  @Autowired
  UrlUtil urlUtil;

    @GetMapping("/avatar")
    public ResponseEntity<Resource> getFile(@RequestParam String url) {
        if (!url.startsWith("http://localhost:8080/doctor_avatars")) {
            return ResponseEntity.badRequest().body(null);
        }
        return urlUtil.getFile(url);
    }
}

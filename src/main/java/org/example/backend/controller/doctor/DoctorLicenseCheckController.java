package org.example.backend.controller.doctor;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.example.backend.entity.admin.LicenseCheck;
import org.example.backend.service.doctor.DoctorLicenseService;
import org.example.backend.util.MultipartFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/doctorLicense")
public class DoctorLicenseCheckController {
  private static final Logger log = LoggerFactory.getLogger(DoctorController.class);
  @Autowired private DoctorLicenseService doctorLicenseService;
  @Autowired private MultipartFileUtil multipartFileUtil;

  @PostMapping("/insert")
  public ResponseEntity<LicenseCheck> insertCheckLicense(
      MultipartFile multipartFile, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String url =
        multipartFileUtil.saveMultipartFile(multipartFile, "LicenseImage/" + doctorId + "/");
    log.info("url:{}", url);
    if (url == null) return ResponseEntity.badRequest().build();
    LicenseCheck licenseCheck = new LicenseCheck();
    licenseCheck.setDoctorId(doctorId);
    licenseCheck.setUrl(url);
    licenseCheck.setStatus("未认证");
    licenseCheck.setCreatedAt(LocalDateTime.now());
    licenseCheck.setUpdatedAt(LocalDateTime.now());
    boolean success = doctorLicenseService.insertCheckLicense(licenseCheck);
    log.info("success:{}", success);
    if (success) {
      return ResponseEntity.ok(licenseCheck);
    }

    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/myLicense")
  public ResponseEntity<List<LicenseCheck>> selectMyLicense(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    List<LicenseCheck> licenseCheck = doctorLicenseService.selectAllCheckLicense(doctorId);
    if (licenseCheck != null) return ResponseEntity.ok(licenseCheck);
    return ResponseEntity.badRequest().build();
  }
}

package org.example.backend.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.entity.user.User;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.service.user.UserService;
import org.example.backend.util.ExcelReader;
import org.example.backend.util.JsonParser;
import org.example.backend.util.MultipartFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/import")
public class ImportController {
  @Autowired JsonParser jsonParser;

  @Autowired ExcelReader excelReader;

  @Autowired UserService userService;

  @Autowired DoctorService doctorService;

  @Autowired MultipartFileUtil multipartFileUtil;

  @PostMapping("/user")
  public ResponseEntity<String> importUser(
      @RequestParam("file") MultipartFile file,
      @RequestParam("urlJson") String urlJson,
      HttpServletRequest request) {
    try {
      String adminId = (String) request.getAttribute("userId");
      if (adminId != null && !adminId.isEmpty() && adminId.charAt(0) == 'A') {
        String url = jsonParser.parseJsonString(urlJson, "url");
        String fileUrl = multipartFileUtil.saveFile(file, url);
        List<User> users = excelReader.readExcel(fileUrl, User.class);
        if (users != null) {
          userService.insertAllUser(users);
          return ResponseEntity.ok("User information added successfully");
        }
      }
      return ResponseEntity.status(400).body("Failed to add user information");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to add user information");
    }
  }

  @PostMapping("/doctor")
  public ResponseEntity<String> importDoctor(
      @RequestParam("file") MultipartFile file,
      @RequestParam("urlJson") String urlJson,
      HttpServletRequest request) {
    try {
      String adminId = (String) request.getAttribute("userId");
      if (adminId != null && !adminId.isEmpty() && adminId.charAt(0) == 'A') {
        String url = jsonParser.parseJsonString(urlJson, "url");
        String fileUrl = multipartFileUtil.saveFile(file, url);
        List<Doctor> doctors = excelReader.readExcel(fileUrl, Doctor.class);
        System.out.println(doctors);
        if (doctors != null) {
          doctorService.insertAllDoctors(doctors);
          return ResponseEntity.ok("Doctor information added successfully");
        }
      }
      return ResponseEntity.status(400).body("Failed to add doctor information");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to add doctor information");
    }
  }
}

package org.example.backend.controller.doctor;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Base64;
import java.util.List;
import org.example.backend.entity.doctor.Doctor;
import org.example.backend.service.doctor.DoctorService;
import org.example.backend.util.ExcelReader;
import org.example.backend.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

  private static final Logger log = LoggerFactory.getLogger(DoctorController.class);
  @Autowired private DoctorService doctorService;

  @Autowired private JsonParser jsonParser;

  @Autowired private ExcelReader excelReader;

  @GetMapping("/selectDoctorCount")
  public ResponseEntity<String> selectDoctorCount(HttpServletRequest request) {
    String adminId = (String) request.getAttribute("userId");
    if (adminId != null && !adminId.isEmpty() && adminId.charAt(0) == 'A') {
      int doctorCount = doctorService.selectDoctorCount();
      return ResponseEntity.ok("{\"doctorCount\":\"" + doctorCount + "\"}");
    } else {
      return ResponseEntity.status(500).body("Failed to Get doctor information");
    }
  }

  @GetMapping("/selectUnqualifiedDoctorCount")
  public ResponseEntity<String> selectUnqualifiedDoctorCount(HttpServletRequest request) {
    String adminId = (String) request.getAttribute("userId");
    if (adminId != null && !adminId.isEmpty() && adminId.charAt(0) == 'A') {
      int unqualifiedDoctorCount = doctorService.selectUnqualifiedDoctorCount();
      return ResponseEntity.ok("{\"unqualifiedDoctorCount\":\"" + unqualifiedDoctorCount + "\"}");
    } else {
      return ResponseEntity.status(500).body("Failed to Get doctor information");
    }
  }

  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAll() {

    // 调用服务层来查询所有医生信息
    List<Doctor> result = doctorService.selectAll();
    for (Doctor doctor : result) {
      doctor.setAvatarUrl(doctorService.getAvatarBase64(doctor.getDoctorId()));
    }
    return ResponseEntity.ok(jsonParser.toJsonFromEntityList(result));
  }

  @GetMapping("/selectById")
  public ResponseEntity<String> selectById(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    // 调用服务层来根据doctorId查询医生信息
    Doctor selectedDoctor = doctorService.selectById(doctorId);

    if (selectedDoctor != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntity(selectedDoctor));
    } else {
      return ResponseEntity.status(500).body("Failed to add doctor information");
    }
  }

  @PostMapping("/ban")
  public ResponseEntity<String> banAccount(@RequestBody String doctorJson) {
    String doctorId = jsonParser.parseJsonString(doctorJson, "doctorId");

    // 调用服务层来禁用医生账户
    boolean success = doctorService.banAccount(doctorId);

    if (success) {
      return ResponseEntity.ok("doctor account disabled successfully");
    }
    return ResponseEntity.status(500).body("Failed to ban doctor account");
  }

  @PostMapping("/active")
  public ResponseEntity<String> activeAccount(@RequestBody String doctorJson) {
    String doctorId = jsonParser.parseJsonString(doctorJson, "doctorId");

    // 调用服务层来禁用医生账户
    boolean success = doctorService.activeAccount(doctorId);

    if (success) {
      return ResponseEntity.ok("doctor account activated successfully");
    }
    return ResponseEntity.status(500).body("Failed to active doctor account");
  }

  // 处理添加医生信息的请求
  @PostMapping("/add")
  public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) {

    // 调用服务层来添加医生信息到数据库
    String result = doctorService.insert(doctor);

    if (result != null) {
      return ResponseEntity.ok("{\\\"doctorId\\\": \\\"\" + result + \"\\\"}");
    } else {
      return ResponseEntity.status(500).body("Failed to add doctor information");
    }
  }

  @PostMapping("/insertAll")
  public ResponseEntity<String> insertAllDoctors(@RequestBody String urlJson) {
    String url = jsonParser.parseJsonString(urlJson, "url");
    List<Doctor> doctors = excelReader.readExcel(url, Doctor.class);
    // 调用服务层来添加医生信息到数据库
    boolean success = doctorService.insertAllDoctors(doctors);
    if (success) {
      return ResponseEntity.ok("doctor information added successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to add doctor information");
    }
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateDoctor(
      @RequestBody Doctor doctor, HttpServletRequest request) {
    doctor.setDoctorId((String) request.getAttribute("userId"));
    // 调用服务层来更新医生信息
    boolean success = doctorService.update(doctor);

    if (success) {
      return ResponseEntity.ok("doctor information updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update doctor information");
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<String> deleteAccount(
      @RequestBody String doctorJson, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String password = jsonParser.parseJsonString(doctorJson, "password");

    if (!doctorService.validatePassword(doctorId, password)) {
      return ResponseEntity.status(400).body("Failed to find doctor information");
    }
    // 调用服务层来删除医生信息
    boolean success = doctorService.delete(doctorId);

    if (success) {
      return ResponseEntity.ok("doctor information deleted successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to delete doctor information");
    }
  }

  @PostMapping("/updatePassword")
  public ResponseEntity<String> updatePassword(
      @RequestBody String doctorJson, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String oldPassword = jsonParser.parseJsonString(doctorJson, "oldPassword");
    if (!doctorService.validatePassword(doctorId, oldPassword)) {
      return ResponseEntity.status(400).body("Failed to find doctor information");
    }
    String newPassword1 = jsonParser.parseJsonString(doctorJson, "newPassword1");
    String newPassword2 = jsonParser.parseJsonString(doctorJson, "newPassword2");
    if (newPassword1 != null && !newPassword1.equals(newPassword2)) {
      return ResponseEntity.status(400).body("Passwords do not match");
    }
    if (doctorService.updatePassword(doctorId, newPassword1)) {
      return ResponseEntity.ok("Password updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update password");
    }
  }

  @GetMapping("/information")
  public ResponseEntity<String> getInformation(HttpServletRequest request) {
    try {
      // 从请求中获取用户ID
      String userId = (String) request.getAttribute("userId");
      Doctor doctor = doctorService.selectById(userId);
      String result =
          jsonParser.removeKeyFromJson(
              jsonParser.removeKeyFromJson(jsonParser.toJsonFromEntity(doctor), "doctorId"),
              "password");
      result = jsonParser.removeKeyFromJson(result, "avatarUrl");
      result = jsonParser.removeKeyFromJson(result, "status");

      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed");
    }
  }

  @PostMapping("/updateData")
  public ResponseEntity<String> updateData(
      @RequestBody String doctorJson, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String name = jsonParser.parseJsonString(doctorJson, "name");
    String username = jsonParser.parseJsonString(doctorJson, "username");
    if (doctorService.isUsernameExist(doctorId, username) != null) {
      return ResponseEntity.status(400).body("Username already exists");
    }
    String phone = jsonParser.parseJsonString(doctorJson, "phone");
    String birthdateStr = jsonParser.parseJsonString(doctorJson, "birthdate");
    Date date = new Date(Long.parseLong(birthdateStr));
    String gender = jsonParser.parseJsonString(doctorJson, "gender");
    String experience = jsonParser.parseJsonString(doctorJson, "experience");
    Doctor doctor = doctorService.selectById(doctorId);
    doctor.setName(name);
    doctor.setUsername(username);
    doctor.setPhone(phone);
    doctor.setGender(gender);
    doctor.setExperience(experience);
    doctor.setBirthdate(date);

    if (doctorService.update(doctor)) {
      return ResponseEntity.ok("Data updated successfully");
    } else {
      return ResponseEntity.status(500).body("Failed to update Data");
    }
  }

  @PostMapping("/upload_avatar_base64")
  public ResponseEntity<String> uploadAvatarBase64(
      @RequestBody String base64Image, HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");

    // 解析 JSON 字符串
    base64Image = jsonParser.parseJsonString(base64Image, "base64Image");

    // 检查 Base64 数据的格式
    if (base64Image == null || !base64Image.startsWith("data:image")) {
      return new ResponseEntity<>("Invalid image format", HttpStatus.BAD_REQUEST);
    }

    // 提取 Base64 字符串中的数据部分
    String[] base64Parts = base64Image.split(",", 2);
    if (base64Parts.length != 2) {
      return new ResponseEntity<>("Invalid Base64 data", HttpStatus.BAD_REQUEST);
    }

    String base64Data = base64Parts[1];

    // 额外的检查：确保 Base64 数据的长度是 4 的倍数
    if (base64Data.length() % 4 != 0) {
      return new ResponseEntity<>(
          "Base64 data length is not a multiple of 4", HttpStatus.BAD_REQUEST);
    }

    try {
      // 解码 Base64 字符串为字节数组
      byte[] imageBytes = Base64.getDecoder().decode(base64Data);

      // 构建文件保存路径
      String folder =
          System.getProperty("user.dir")
              + File.separator
              + "uploads"
              + File.separator
              + "doctor_avatars"
              + File.separator;
      String fileName = doctorId + ".jpg";

      // 确保文件夹存在
      File directory = new File(folder);
      if (!directory.exists()) {
        directory.mkdirs();
      }

      // 保存文件
      Path path = Paths.get(folder + fileName);
      Files.write(path, imageBytes);

      // 返回图片的 URL
      String imageUrl = "http://localhost:8080/doctor_avatars/" + fileName;
      Doctor doctor = doctorService.selectById(doctorId);
      doctor.setAvatarUrl(imageUrl);

      if (!doctorService.update(doctor)) {
        return ResponseEntity.status(500).body("Failed to upload avatar");
      }

      return ResponseEntity.ok("Successfully uploaded");
    } catch (IllegalArgumentException | IOException e) {
      log.error("Error occurred while uploading Base64 image", e);
      return new ResponseEntity<>(
          "Error occurred while uploading image", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get_avatar_base64")
  public ResponseEntity<String> getAvatarBase64(HttpServletRequest request) {
    String doctorId = (String) request.getAttribute("userId");
    String folder =
        System.getProperty("user.dir")
            + File.separator
            + "uploads"
            + File.separator
            + "doctor_avatars"
            + File.separator;
    String fileName = doctorId + ".jpg";
    Path path = Paths.get(folder + fileName);
    log.info(folder + fileName);
    try {
      // 读取文件内容为字节数组
      byte[] imageBytes = Files.readAllBytes(path);

      // 编码为Base64字符串
      String base64Image =
          "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageBytes);

      return ResponseEntity.ok("{\"base64Image\": \"" + base64Image + "\"}");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}

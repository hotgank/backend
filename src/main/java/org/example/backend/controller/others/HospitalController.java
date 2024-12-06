package org.example.backend.controller.others;

import java.util.List;
import org.example.backend.dto.AdminGetHospitalDTO;
import org.example.backend.dto.HospitalPageResult;
import org.example.backend.entity.others.Hospital;
import org.example.backend.service.others.HospitalService;
import org.example.backend.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {
  @Autowired private HospitalService hospitalService;

  @Autowired private JsonParser jsonParser;

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(HospitalController.class);

  /**
   * 获取所有医院
   *
   * @return 医院列表
   */
  @GetMapping("/selectAll")
  public ResponseEntity<String> selectAllHospitals() {
    List<Hospital> hospitals = hospitalService.selectAllHospitals();

    if (hospitals != null) {
      return ResponseEntity.ok(jsonParser.toJsonFromEntityList(hospitals));
    } else {
      return ResponseEntity.status(500).body("Failed to find hospitals");
    }
  }

  /**
   * 获取医院数量
   *
   * @return 数量
   */
  @GetMapping("/getHospitalCount")
  public ResponseEntity<String> getHospitalCount() {
    int count = hospitalService.getHospitalCount();
    if (count != -1) {
      return ResponseEntity.ok("{\"count\":\"" + count + "\"}");
    } else {
      return ResponseEntity.status(500).body("Failed to get hospital count");
    }
  }

  /**
   * 插入医院
   *
   * @param hospital 医院对象
   * @return 插入结果
   */
  @PostMapping("/insertHospital")
  public ResponseEntity<String> insertHospital(@RequestBody Hospital hospital) {
    int result = hospitalService.insertHospital(hospital);
    if (result == 1) {
      return ResponseEntity.ok("Success");
    } else {
      return ResponseEntity.status(500).body("Failed to insert hospital");
    }
  }

  /**
   * 删除医院
   *
   * @param jsonString json格式字符串，包含医院名
   * @return 删除结果
   */
  @PostMapping("/deleteHospital")
  public ResponseEntity<String> deleteHospital(@RequestBody String jsonString) {
    String hospitalName = jsonParser.parseJsonString(jsonString, "hospitalName");
    int result = hospitalService.deleteHospital(hospitalName);
    if (result == 1) {
      return ResponseEntity.ok("Success");
    } else {
      return ResponseEntity.status(500).body("Failed to delete hospital");
    }
  }

  /**
   * 更新医院信息
   *
   * @param hospital 医院对象
   * @return
   */
  @PostMapping("/updateHospital")
  public ResponseEntity<String> updateHospital(@RequestBody Hospital hospital) {
    int result = hospitalService.updateHospital(hospital);
    if (result == 1) {
      return ResponseEntity.ok("Success");
    } else {
      return ResponseEntity.status(500).body("更新失败，检查信息是否未填写或医院信息已存在");
    }
  }

  /**
   * 更新医院管理员
   *
   * @param hospital 医院对象
   * @return 更新结果
   */
  @PostMapping("/updateAdminId")
  public ResponseEntity<String> updateAdminId(@RequestBody Hospital hospital) {
    int result = hospitalService.updateAdminId(hospital);
    if (result == 1) {
      return ResponseEntity.ok("Success");
    } else {
      return ResponseEntity.status(500).body("Failed to update hospital");
    }
  }

  /**
   * 根据条件查询医院
   *
   * @param jsonString json格式字符串，包含查询条件queryString,currentPage,pageSize
   * @return 医院分页查询列表
   */
  @PostMapping("/selectHospitalByCondition")
  public ResponseEntity<List<AdminGetHospitalDTO>> selectHospitalByCondition(
      @RequestBody String jsonString) {
    String queryString = jsonParser.parseJsonString(jsonString, "queryString");
    int currentPage = jsonParser.parseJsonInt(jsonString, "currentPage");
    int pageSize = jsonParser.parseJsonInt(jsonString, "pageSize");
    HospitalPageResult hospitalPageResult =
        hospitalService.selectHospitalByCondition(currentPage, pageSize, queryString);
    if (hospitalPageResult == null) {
      return ResponseEntity.status(500).body(null);
    }
    logger.info("结果数量: {}", hospitalPageResult.getTotal());
    return ResponseEntity.ok(hospitalPageResult.getRows());
  }
}

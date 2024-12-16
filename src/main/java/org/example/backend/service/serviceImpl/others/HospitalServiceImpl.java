package org.example.backend.service.serviceImpl.others;

import com.github.pagehelper.PageInfo;
import java.util.List;
import org.example.backend.dto.AdminGetHospitalDTO;
import org.example.backend.dto.HospitalPageResult;
import org.example.backend.entity.others.Hospital;
import org.example.backend.mapper.admin.AdminMapper;
import org.example.backend.mapper.others.HospitalMapper;
import org.example.backend.service.others.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;

@Service
public class HospitalServiceImpl implements HospitalService {

  private static final Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

  @Autowired private HospitalMapper hospitalMapper;

  @Autowired private AdminMapper adminMapper;

  @Override
  public List<Hospital> selectAllHospitals() {
    try {
      return hospitalMapper.selectAll();
    } catch (Exception e) {
      // 记录异常日志
      logger.error("获取医院列表失败", e);
      return null;
    }
  }

  @Override
  public int getHospitalCount() {
    try {
      return hospitalMapper.getHospitalCount();
    } catch (Exception e) {
      logger.error("获取医院列表数量失败", e);
      return 0;
    }
  }

  @Override
  public int insertHospital(Hospital hospital) {
    try {
      // 检查医院名和地址是否为空
      if (hospital.getHospitalName() == null || hospital.getAddress() == null) {
        logger.warn("医院名或地址为空，插入失败");
        return 0;
      }
      // 检查是否重复(医院名和地址都相同)
      if (hospitalMapper.selectAll().stream()
          .anyMatch(
              h ->
                  h.getHospitalName().equals(hospital.getHospitalName())
                      && h.getAddress().equals(hospital.getAddress()))) {
        logger.warn("医院名或地址重复，插入失败");
        return 0;
      }
      return hospitalMapper.insertHospital(hospital);
    } catch (Exception e) {
      logger.error("插入医院信息失败", e);
      return 0;
    }
  }

  @Override
  public int deleteHospital(String hospitalName) {
    try {
      return hospitalMapper.deleteHospital(hospitalName);
    } catch (Exception e) {
      logger.error("删除医院信息失败", e);
      return 0;
    }
  }

  @Override
  public int updateAdminId(Hospital hospital) {
    try {
      // 检查医院名是否为空
      if (hospital.getHospitalName() == null) {
        logger.warn("医院名为空，更新失败");
        return 0;
      }
      // 检查管理员ID是否为空
      if (hospital.getAdminId() == null) {
        logger.warn("管理员ID为空，更新失败");
        return 0;
      }
      // 检查管理员是否存在
      if (adminMapper.selectById(hospital.getAdminId()) == null) {
        logger.warn("管理员不存在，更新失败");
        return 0;
      }
      return hospitalMapper.updateAdminId(hospital);
    } catch (Exception e) {
      logger.error("更新医院管理员ID失败", e);
      return 0;
    }
  }

  @Override
  public int updateHospital(Hospital hospital) {
    try {
      // 检查医院名，地址是否为空
      if (hospital.getHospitalName() == null || hospital.getAddress() == null) {
        logger.warn("医院名或地址为空，更新失败");
        return 0;
      }
      // 检查是否重复(医院名和地址都相同)
      if (hospitalMapper.selectAll().stream()
          .anyMatch(
              h ->
                  h.getHospitalName().equals(hospital.getHospitalName())
                      && h.getAddress().equals(hospital.getAddress()))) {
        logger.warn("医院名或地址重复，更新失败");
        return 0;
      }
      return hospitalMapper.updateHospital(hospital);
    } catch (Exception e) {
      logger.error("更新医院信息失败", e);
      return 0;
    }
  }

  @Override
  public HospitalPageResult selectHospitalByCondition(
      int currentPage, int pageSize, String queryString) {
    logger.info("根据条件查询医院信息，当前页: {}, 每页记录数: {}, 查询条件: {}", currentPage, pageSize, queryString);
    try {
      // 启动分页
      PageHelper.startPage(currentPage, pageSize);
      logger.info(
          "PageHelper 分页信息: pageNum={}, pageSize={}, startRow={}, endRow={}, total={}, pages={}",
          PageHelper.getLocalPage().getPageNum(),
          PageHelper.getLocalPage().getPageSize(),
          PageHelper.getLocalPage().getStartRow(),
          PageHelper.getLocalPage().getEndRow(),
          PageHelper.getLocalPage().getTotal(),
          PageHelper.getLocalPage().getPages());

      // 获取表中数据数量
      int total = hospitalMapper.getHospitalCount();
      logger.info("获取医院列表总数成功，总数: {}", total);
      // 判断分页条件是否合理
      // 不能小于0
      if (currentPage < 1) {
        logger.warn("当前页数不能小于1，设置当前页数为1");
        currentPage = 1;
      }
      if (pageSize < 1) {
        logger.warn("每页记录数不能小于1，设置每页记录数为1");
        pageSize = 1;
      }
      // 根据pageSize计算总页数，判断currentPage是否合理
      int totalPages = (int) Math.ceil((double) total / pageSize);
      if (currentPage > totalPages && totalPages != 0) {
        logger.warn("当前页数不能大于总页数，设置当前页数为{}", totalPages);
        currentPage = totalPages;
      }

      // 执行查询，现在返回的是 List 而不是 Page
      List<AdminGetHospitalDTO> list =
          hospitalMapper.selectHospitalByCondition(
              queryString, (currentPage - 1) * pageSize, pageSize);

      // 获取分页信息
      PageInfo<AdminGetHospitalDTO> pageInfo = new PageInfo<>(list);

      logger.info("根据条件查询医院信息成功，总记录数: {}, 结果: {}", pageInfo.getTotal(), list);
      logger.info(
          "PageHelper 分页信息: pageNum={}, pageSize={}, startRow={}, endRow={}, total={}, pages={}",
          pageInfo.getPageNum(),
          pageInfo.getPageSize(),
          pageInfo.getStartRow(),
          pageInfo.getEndRow(),
          pageInfo.getTotal(),
          pageInfo.getPages());
      return new HospitalPageResult(pageInfo.getTotal(), list);
    } catch (Exception e) {
      logger.error("根据条件查询医院信息失败", e);
      return null;
    }
  }
}

package org.example.backend.service.others;

import java.util.List;
import org.example.backend.dto.HospitalPageResult;
import org.example.backend.entity.others.Hospital;

public interface HospitalService {

  /**
   * 查询所有医院
   *
   * @return 医院列表
   */
  List<Hospital> selectAllHospitals();

  /**
   * 查询医院数量
   *
   * @return 医院数量
   */
  int getHospitalCount();

  /**
   * 插入医院
   *
   * @param hospital 医院对象
   * @return 插入成功行数
   */
  int insertHospital(Hospital hospital);

  /**
   * 删除医院
   *
   * @param hospitalName 医院名
   * @return 删除成功行数
   */
  int deleteHospital(String hospitalName);

    int deleteAdmin(String hospitalName);

    /**
   * 更新医院管理员
   *
   * @param hospital 医院对象
   * @return 更新成功行数
   */
  int updateAdminId(Hospital hospital);

  /**
   * 更新医院信息
   *
   * @param hospital 医院对象
   * @return 更新成功行数
   */
  int updateHospital(Hospital hospital);

  /**
   * 条件查询医院
   *
   * @param currentPage 当前页码
   * @param pageSize 页大小
   * @param queryString 查询条件（关键字）
   * @return 医院分页结果
   */
  HospitalPageResult selectHospitalByCondition(
      int currentPage, int pageSize, String queryString);
}

package org.example.backend.mapper.others;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.example.backend.dto.AdminGetHospitalDTO;
import org.example.backend.entity.others.Hospital;
import org.example.backend.util.SqlProvider.HospitalSqlProvider;

@Mapper
public interface HospitalMapper {
  /**
   * 查询所有医院
   *
   * @return 医院列表
   */
  @Select("SELECT * FROM o_hospitals")
  @Results({
    @Result(column = "hospital_name", property = "hospitalName"),
    @Result(column = "address", property = "address"),
    @Result(column = "admin_id", property = "adminId"),
  })
  List<Hospital> selectAll();

  /**
   * 获取医院数量
   *
   * @return 医院数量
   */
  @Select("SELECT COUNT(*) FROM o_hospitals")
  int getHospitalCount();

  /**
   * 插入医院
   *
   * @param hospital 要插入的医院对象，包含医院名称、地址和管理员ID
   * @return 插入成功行数
   */
  @Insert(
      "INSERT INTO o_hospitals(hospital_name, address, admin_id) VALUES(#{hospitalName}, #{address}, #{adminId})")
  int insertHospital(Hospital hospital);

  /**
   * 删除医院
   *
   * @param hospitalName 要删除的医院名称
   * @return 删除成功行数
   */
  @Delete("DELETE FROM o_hospitals WHERE hospital_name = #{hospitalName}")
  int deleteHospital(String hospitalName);

  /**
   * 更新医院地址
   *
   * @param hospital 要更新的医院对象，包含医院名称、地址和管理员ID
   * @return 更新成功行数
   */
  @Update(
      "UPDATE o_hospitals SET hospital_name = #{hospitalName}, address = #{address} WHERE hospital_name = #{hospitalName}")
  int updateHospital(Hospital hospital);

  /**
   * 更新医院管理员ID
   *
   * @param hospital 要更新的医院对象，包含医院名称、地址和管理员ID
   * @return 更新成功行数
   */
  @Update("UPDATE o_hospitals SET admin_id = #{adminId} WHERE hospital_name = #{hospitalName}")
  int updateAdminId(Hospital hospital);

  /**
   * 根据条件查询医院，返回分页查询结果
   *
   * @param queryString 查询条件
   * @return 分页查询结果
   */
  @SelectProvider(type = HospitalSqlProvider.class, method = "selectHospitalByCondition")
  @Results({
    @Result(column = "hospital_name", property = "hospitalName"),
    @Result(column = "address", property = "address"),
    @Result(column = "admin_id", property = "adminId"),
    @Result(column = "adminUsername", property = "adminUsername")
  })
  List<AdminGetHospitalDTO> selectHospitalByCondition(
      @Param("queryString") String queryString,
      @Param("pageBegin") int PageBegin,
      @Param("PageSize") int PageSize);
}

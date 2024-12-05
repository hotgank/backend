package org.example.backend.util.SqlProvider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class HospitalSqlProvider {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(HospitalSqlProvider.class);

  /**
   * 构造查询语句
   *
   * @param queryString 查询条件
   * pageBegin 页数
   * pageSize 每页数量
   * @return 查询语句
   */
  public String selectHospitalByCondition(
      @Param("queryString") String queryString,
      @Param("pageBegin") int PageBegin,
      @Param("PageSize") int PageSize) {
    String sql =
        new SQL() {
          {
            SELECT("hospital_name, address, o_hospitals.admin_id, username as adminUsername");
            FROM("o_hospitals");
            LEFT_OUTER_JOIN("a_admins ON o_hospitals.admin_id = a_admins.admin_id");
            if (queryString != null && !queryString.isEmpty()) {
              WHERE("hospital_name LIKE CONCAT('%', #{queryString}, '%')");
              OR();
              WHERE("address LIKE CONCAT('%', #{queryString}, '%')");
              OR();
              WHERE("username LIKE CONCAT('%', #{queryString}, '%')");
            }
            LIMIT("#{pageBegin}, #{PageSize}");
          }
        }.toString();
    logger.info("查询语句：{}", sql);
    return sql;
  }
}

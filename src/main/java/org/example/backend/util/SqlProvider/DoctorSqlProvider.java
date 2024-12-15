package org.example.backend.util.SqlProvider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class DoctorSqlProvider {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(DoctorSqlProvider.class);

  /**
   * 根据条件构造语句查询医生
   *
   * @param queryString 查询条件
   * @param PageBegin 页数
   * @param PageSize 每页数量
   * @return 查询语句
   */
  public String selectDoctorByCondition(
      @Param("queryString") String queryString,
      @Param("pageBegin") int PageBegin,
      @Param("PageSize") int PageSize) {
    logger.info("selectDoctorByCondition");
    String sql =
        new SQL() {
          {
            SELECT("*");
            FROM("d_doctors");
            if (queryString != null && !queryString.equals("")) {
              WHERE("name like '%" + queryString + "%'");
              OR();
              WHERE("doctor_id LIKE CONCAT('%', #{queryString}, '%')");
            }
            ORDER_BY("rating DESC");
            LIMIT("#{pageBegin}, #{PageSize}");
          }
        }.toString();
    logger.info("查询语句：{}", sql);
    return sql;
  }
}

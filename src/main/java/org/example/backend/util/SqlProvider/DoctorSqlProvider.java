package org.example.backend.util.SqlProvider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoctorSqlProvider {
  private static final Logger logger =
      LoggerFactory.getLogger(DoctorSqlProvider.class);

  /**
   * 根据条件构造语句查询医生
   *
   * @param queryString 查询条件
   * @param pageBegin 页数
   * @param pageSize 每页数量
   * @return 查询语句
   */
  public String selectDoctorByCondition(
          @Param("queryString") String queryString,
          @Param("adminId") String adminId,
          @Param("pageBegin") int pageBegin,
          @Param("pageSize") int pageSize) {
    logger.info("selectDoctorByCondition");
    String sql =
        new SQL() {
          {
            SELECT("*");
            FROM("d_doctors d");
            LEFT_OUTER_JOIN("o_hospitals h ON d.workplace = h.hospital_name");
            LEFT_OUTER_JOIN("a_admins a ON a.admin_id = #{adminId}");

            WHERE("(h.admin_id = #{adminId} " +
                    "OR (a.admin_type = 'first' AND h.admin_id IS NULL) " +
                    "OR a.admin_type = 'super')");
            if (queryString != null && !queryString.isEmpty()) {
              WHERE(
                      "(name like concat('%', #{queryString}, '%')"
                              + " OR "
                              + "doctor_id LIKE concat('%', #{queryString}, '%'))"
              );
            }
            ORDER_BY("rating DESC");
            LIMIT("#{pageBegin}, #{pageSize}");
          }
        }.toString();

    logger.info("查询语句：{}", sql);
    return sql;
  }
}

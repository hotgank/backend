package org.example.backend.util.SqlProvider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(UserSqlProvider.class);

  /**
   * 根据条件查询用户
   *
   * @param queryString 查询条件
   * @param PageBegin 页码
   * @param PageSize 页大小
   * @return sql语句
   */
  public String selectUserByCondition(
      @Param("queryString") String queryString,
      @Param("pageBegin") int PageBegin,
      @Param("PageSize") int PageSize) {
    logger.info("selectUserByCondition");
    String sql =
        new SQL() {
          {
            SELECT("*");
            FROM("u_users");
            if (queryString != null && !queryString.equals("")) {
              WHERE(
                  "(username like concat('%', #{queryString}, '%')"
                      + " OR "
                      + "user_id LIKE concat('%', #{queryString}, '%'))"
              );
            }
            LIMIT("#{pageBegin}, #{PageSize}");
          }
        }.toString();
    logger.info("查询语句：{}", sql);
    return sql;
  }
}

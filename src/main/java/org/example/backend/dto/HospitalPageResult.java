package org.example.backend.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */
public class HospitalPageResult implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long total;//总记录数
  private List<AdminGetHospitalDTO> rows;//当前页结果

  //构造函数
  public HospitalPageResult(Long total, List<AdminGetHospitalDTO> rows) {
    this.total = total;
    this.rows = rows;
  }

  //getter和setter方法
  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }

  public void setRows(List<AdminGetHospitalDTO> rows) {
    this.rows = rows;
  }

  public List<AdminGetHospitalDTO> getRows() {
    return rows;
  }
}

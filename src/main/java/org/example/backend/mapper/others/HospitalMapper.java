package org.example.backend.mapper.others;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.others.Hospital;

@Mapper
public interface HospitalMapper {
  @Select("SELECT * FROM o_hospitals")
  @Results({
    @Result(column = "hospital_name", property = "hospitalName"),
    @Result(column = "address", property = "address"),
    @Result(column = "admin_id", property = "adminId"),
  })
  List<Hospital> selectAll();
}

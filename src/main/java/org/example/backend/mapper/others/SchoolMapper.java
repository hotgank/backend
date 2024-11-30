package org.example.backend.mapper.others;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.others.School;

@Mapper
public interface SchoolMapper {
  @Select("SELECT * FROM o_schools")
  @Results({
    @Result(column = "address", property = "address"),
    @Result(column = "admin_id", property = "adminId"),
    @Result(column = "school_name", property = "schoolName")
  })
  List<School> selectAll();
}

package org.example.backend.mapper.others;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.others.Hospital;

@Mapper
public interface HospitalMapper {
  @Select("SELECT * FROM o_hospitals")
  List<Hospital> selectAll();
}

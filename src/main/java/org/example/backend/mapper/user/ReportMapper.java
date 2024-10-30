package org.example.backend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.user.Report;

@Mapper
public interface ReportMapper extends BaseMapper<Report> {
}
package org.example.backend.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.admin.AdminLog;

@Mapper
public interface AdminLogMapper extends BaseMapper<AdminLog>{
}
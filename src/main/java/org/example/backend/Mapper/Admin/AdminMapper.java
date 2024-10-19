package org.example.backend.Mapper.Admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.Entity.Admin.Admin;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
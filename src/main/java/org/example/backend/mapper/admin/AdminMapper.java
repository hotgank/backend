package org.example.backend.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.admin.Admin;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}

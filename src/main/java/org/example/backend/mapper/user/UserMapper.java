package org.example.backend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.user.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
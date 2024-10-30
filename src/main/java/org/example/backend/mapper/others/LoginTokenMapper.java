package org.example.backend.mapper.others;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.others.LoginToken;

@Mapper
public interface LoginTokenMapper extends BaseMapper<LoginToken> {
}
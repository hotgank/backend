package org.example.backend.Mapper.Others;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.Entity.Others.LoginToken;

@Mapper
public interface LoginTokenMapper extends BaseMapper<LoginToken> {
}
package org.example.backend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.user.WeChatUser;

@Mapper
public interface WeChatUserMapper extends BaseMapper<WeChatUser> {
}
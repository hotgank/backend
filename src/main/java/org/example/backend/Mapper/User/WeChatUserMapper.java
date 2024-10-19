package org.example.backend.Mapper.User;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.Entity.User.WeChatUser;

@Mapper
public interface WeChatUserMapper extends BaseMapper<WeChatUser> {
}
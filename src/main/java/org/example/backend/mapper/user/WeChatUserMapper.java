package org.example.backend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.user.WeChatUser;

@Mapper
public interface WeChatUserMapper extends BaseMapper<WeChatUser> {

  //根据openid查询
  @Select("SELECT * FROM w_wechat_users WHERE openid = #{openid}")
  WeChatUser selectByOpenid(String openid);
}
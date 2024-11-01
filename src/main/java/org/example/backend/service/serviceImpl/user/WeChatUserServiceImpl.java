package org.example.backend.service.serviceImpl.user;

import java.util.List;
import org.example.backend.entity.user.WeChatUser;
import org.example.backend.mapper.user.WeChatUserMapper;
import org.example.backend.service.user.WeChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeChatUserServiceImpl implements WeChatUserService {
  @Autowired
  private WeChatUserMapper weChatUserMapper;

  @Override
  public List<WeChatUser> getAllWeChatUsers() {
    return weChatUserMapper.selectList(null);
  }

  @Override
  public WeChatUser getWeChatUserById(String id) {
    return weChatUserMapper.selectById(id);
  }

  @Override
  public int createWeChatUser(WeChatUser weChatUser) {
    return weChatUserMapper.insert(weChatUser);
  }

  @Override
  public int updateWeChatUser(WeChatUser weChatUser) {
    return weChatUserMapper.updateById(weChatUser);
  }

  @Override
  public int deleteWeChatUserById(String id) {
    return weChatUserMapper.deleteById(id);
  }
}
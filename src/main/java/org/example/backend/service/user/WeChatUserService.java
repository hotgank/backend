package org.example.backend.service.user;

import java.util.List;
import org.example.backend.entity.user.WeChatUser;

public interface WeChatUserService {
  List<WeChatUser> getAllWeChatUsers();
  WeChatUser getWeChatUserById(String id);
  int createWeChatUser(WeChatUser weChatUser);
  int updateWeChatUser(WeChatUser weChatUser);
  int deleteWeChatUserById(String id);
}

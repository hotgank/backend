package org.example.backend.Service.User;

import java.util.List;
import org.example.backend.Entity.User.WeChatUser;

public interface WeChatUserService {
  List<WeChatUser> getAllWeChatUsers();
  WeChatUser getWeChatUserById(String id);
  int createWeChatUser(WeChatUser weChatUser);
  int updateWeChatUser(WeChatUser weChatUser);
  int deleteWeChatUserById(String id);
}

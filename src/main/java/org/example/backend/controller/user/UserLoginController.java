package org.example.backend.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.example.backend.entity.user.User;
import org.example.backend.entity.user.WeChatUser;
import org.example.backend.service.user.UserService;
import org.example.backend.service.user.WeChatUserService;
import org.example.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.web.client.RestTemplate;
import org.example.backend.util.RedisUtil;

/**
 * 微信用户登录控制器
 * @author Q
 */
@RestController
@RequestMapping("/api/userLogin")
public class UserLoginController {

  @Autowired
  private UserService userService;

  @Autowired
  private WeChatUserService weChatUserService;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private RedisUtil redisUtil;

  @PostMapping("/weChatUserLogin")
  public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
    String appId = body.get("appId");
    String appSecret = body.get("appSecret");
    String code = body.get("code");

    //向微信服务器微信用户获取OpenID
    Map<String, String> weChatResponse = getOpenIdAndSessionKeyFromWeChat(appId, appSecret, code);
    if ( !weChatResponse.containsKey("openid") || !weChatResponse.containsKey("session_key")) {
      return ResponseEntity.badRequest().body("Failed to get openid or session_key from WeChat API");
    }
    String openId = weChatResponse.get("openid");
    String sessionKey = weChatResponse.get("session_key");

    try {
      // 查找微信用户表
      WeChatUser weChatUser = weChatUserService.getWeChatUserByOpenId(openId);
      // 判断微信用户是否已存在
      if (weChatUser == null) {
        // 创建新用户
        String userId = userService.register(new User());
        weChatUser.setOpenid(openId);
        weChatUser.setUserId(userId);
        weChatUser.setSessionKey(sessionKey);
        weChatUserService.createWeChatUser(weChatUser);
      } else {
        // 如果微信用户存在，则更新用户信息
        weChatUser.setSessionKey(sessionKey);
        weChatUserService.updateWeChatUser(weChatUser);
      }

      // 生成JWT令牌
      String jwtToken = jwtUtil.generateToken(weChatUser.getUserId());

      // 将令牌存储在Redis中
      redisUtil.storeTokenInRedis(weChatUser.getUserId(), jwtToken);

      // 构建并返回登录响应，返回openID和令牌
      return ResponseEntity.ok("{\"userId\":\"" + weChatUser.getOpenid() + "\",\"token\":\"" + jwtToken + "\"}");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Failed to create user or update user information");
    }
  }

  private Map<String, String> getOpenIdAndSessionKeyFromWeChat(String appId, String appSecret, String code) {
    String url = "https://api.weixin.qq.com/sns/jscode2session" +
        "?appid=" + appId +
        "&secret=" + appSecret +
        "&js_code=" + code +
        "&grant_type=authorization_code";

    RestTemplate restTemplate = new RestTemplate();
    String response = restTemplate.getForObject(url, String.class);

    // 解析响应，获取openid和session_key
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(response);
      String openid = jsonNode.get("openid").asText();
      String session_key = jsonNode.get("session_key").asText();

      return Map.of("openid", openid, "session_key", session_key);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse response from WeChat API", e);
    }
  }
}

package org.example.backend.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.entity.user.User;
import org.example.backend.service.user.UserService;
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
  private JwtUtil jwtUtil;

  @Autowired
  private RedisUtil redisUtil;

  @PostMapping("/weChatUserLogin")
  public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
    String appId = body.get("appId");
    String appSecret = body.get("appSecret");
    String code = body.get("code");
    //用户名
    String username = body.get("username");
    //头像url
    String avatarUrl = body.get("avatarUrl");

    //向微信服务器微信用户获取OpenID
    Map<String, String> weChatResponse = getOpenIdAndSessionKeyFromWeChat(appId, appSecret, code);
    if ( !weChatResponse.containsKey("openid") || !weChatResponse.containsKey("session_key")) {
      return ResponseEntity.badRequest().body("Failed to get openid or session_key from WeChat API");
    }
    String openId = weChatResponse.get("openid");
    String sessionKey = weChatResponse.get("session_key");

    try {
      User user = userService.selectByOpenId(openId);
      if (user == null) {
        // 创建新用户
        user = new User();
        user.setOpenid(openId);
        user.setSessionKey(sessionKey);
        user.setUsername(username);
        user.setAvatarUrl(avatarUrl);
        userService.register(user);
      }
      else {
        // 如果微信用户存在，则更新用户信息
        user.setOpenid(openId);
        user.setSessionKey(sessionKey);
        user.setUsername(username);
        user.setAvatarUrl(avatarUrl);
        userService.update(user);
      }

      // 生成JWT令牌
      String jwtToken = jwtUtil.generateToken(user.getUserId());

      // 将令牌存储在Redis中
      redisUtil.storeTokenInRedis(user.getUserId(), jwtToken);

      return ResponseEntity.ok("{\"openid\":\"" + user.getOpenid() + "\",\"token\":\"" + jwtToken + "\"}");
    }
    catch (Exception e) {
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

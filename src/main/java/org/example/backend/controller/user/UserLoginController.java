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
import org.example.backend.util.WeChatDecryptUtil; // 新增解密工具类

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

  @Autowired
  private WeChatDecryptUtil weChatDecryptUtil;  // 注入解密工具类

  @PostMapping("/weChatUserLogin")
  public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
    //String appId = body.get("appId");
    String appId = "wx975451ebbab26b24";
    //String appSecret = body.get("appSecret");
    String appSecret = "96dd25e0926a1448a51d00560736e451";
    String code = body.get("code");
    String encryptedData = body.get("encryptedData");  // 前端传来的加密数据
    String iv = body.get("iv");  // 前端传来的iv

    // 向微信服务器微信用户获取OpenID
    Map<String, String> weChatResponse = getOpenIdAndSessionKeyFromWeChat(appId, appSecret, code);
    if (!weChatResponse.containsKey("openid") || !weChatResponse.containsKey("session_key")) {
      return ResponseEntity.badRequest().body("Failed to get openid or session_key from WeChat API");
    }
    String openId = weChatResponse.get("openid");
    String sessionKey = weChatResponse.get("session_key");
    //System.out.println("openId: " + openId);

    try {
      // 解密获取用户的敏感数据（头像、昵称、电话）
      String userInfo = weChatDecryptUtil.decryptUserInfo(encryptedData, iv, sessionKey);

      //System.out.println("解密后的用户信息：" + userInfo);

      // 解析解密后的用户信息
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode userJson = objectMapper.readTree(userInfo);
      String username = userJson.get("nickName").asText();
      String avatarUrl = userJson.get("avatarUrl").asText();
      String phone = userJson.has("phoneNumber") ? userJson.get("phoneNumber").asText() : "";

      User user = userService.selectByOpenId(openId);
      if (user == null) {
        // 创建新用户
        user = new User();
        user.setOpenid(openId);
        //System.out.println("userOpenid" + user.getOpenid());
        user.setSessionKey(sessionKey);
        user.setUsername(username);
        user.setAvatarUrl(avatarUrl);
        user.setPhone(phone);
        user.setUserId(userService.register(user));
      } else {
        // 如果微信用户存在，则更新用户信息
        user.setSessionKey(sessionKey);
        user.setUsername(username);
        user.setAvatarUrl(avatarUrl);
        user.setPhone(phone);
        userService.update(user);
      }


      // 生成JWT令牌
      String jwtToken = jwtUtil.generateToken(user.getUserId());
      //System.out.println("jwtToken: " + jwtToken);

      // 将令牌存储在Redis中
      redisUtil.storeTokenInRedis(user.getUserId(), jwtToken);

      // 返回openid和令牌
      return ResponseEntity.ok("{\"openid\":\"" + user.getOpenid() + "\",\"token\":\"" + jwtToken + "\"}");
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
      String openid = jsonNode.get("openid") != null ? jsonNode.get("openid").asText() : null;
      String session_key = jsonNode.get("session_key") != null ? jsonNode.get("session_key").asText() : null;

      if (openid == null || session_key == null) {
        throw new RuntimeException("Failed to get openid or session_key from WeChat API response: " + response);
      }
      //控制台输出

      return Map.of("openid", openid, "session_key", session_key);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse response from WeChat API", e);
    }
  }
}

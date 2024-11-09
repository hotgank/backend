package org.example.backend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Component
public class WeChatDecryptUtil {

  // 解密微信小程序的用户数据
  public String decryptUserInfo(String encryptedData, String iv, String sessionKey) throws Exception {
    // 解码 Base64
    byte[] encryptedDataByte = Base64.decodeBase64(encryptedData);
    byte[] ivByte = Base64.decodeBase64(iv);
    byte[] sessionKeyByte = Base64.decodeBase64(sessionKey);

    // 使用 AES 解密
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
    SecretKeySpec keySpec = new SecretKeySpec(sessionKeyByte, "AES");
    cipher.init(Cipher.DECRYPT_MODE, keySpec, new javax.crypto.spec.IvParameterSpec(ivByte));
    byte[] decrypted = cipher.doFinal(encryptedDataByte);

    // 转换为 UTF-8 字符串
    String result = new String(decrypted, "UTF-8");

    // 使用 Jackson 解析解密后的 JSON 数据
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(result);

    // 如果解密成功且没有错误码，则返回解密后的数据
    if (jsonNode.has("errCode") && jsonNode.get("errCode").asInt() == 0) {
      return result;  // 返回解密后的用户信息
    } else {
      throw new Exception("Failed to decrypt user data");
    }
  }
}

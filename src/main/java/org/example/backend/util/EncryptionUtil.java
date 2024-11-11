package org.example.backend.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

  // 使用MD5进行哈希加密
  public String encryptMD5(String data) {
    return DigestUtils.md5Hex(data);
  }

  // 验证输入的data加密后的哈希值是否与给定的哈希值相等
  public boolean verifyMD5(String data, String encryptedHash) {
    String hashedData = encryptMD5(data);
    return hashedData.equals(encryptedHash);
  }
}

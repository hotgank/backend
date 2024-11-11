package org.example.backend.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

  /**
   * 使用MD5算法进行哈希加密
   *
   * @param data 需要加密的原始数据
   * @return 加密后的数据字符串
   */
  public String encryptMD5(String data) {
    return DigestUtils.md5Hex(data);
  }

  /**
   * 验证输入的data加密后的哈希值是否与给定的哈希值相等
   *
   * @param data        需要验证的原始数据
   * @param encryptedHash 给定的哈希值，用于比较
   * @return            如果加密后的数据与给定的哈希值相等，则返回true；否则返回false
   */
  public boolean verifyMD5(String data, String encryptedHash) {
    // 加密输入的数据，并将结果存储在hashedData中
    String hashedData = encryptMD5(data);
    // 比较加密后的数据与给定的哈希值是否相等
    return hashedData.equals(encryptedHash);
  }
}

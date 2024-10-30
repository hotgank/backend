package org.example.backend.entity.others;

import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;

public class LoginToken {
  @TableId
  private String accountId;
  private String userType;
  private String username;
  private String password;
  private String phone;
  private String email;
  private String phoneCode;
  private String emailCode;
  private Date phoneCodeUpdateTime;
  private Date emailCodeUpdateTime;
  private String loginToken;

  // Getters and Setters
  public String getAccountId() { return accountId; }
  public void setAccountId(String accountId) { this.accountId = accountId; }

  public String getUserType() { return userType; }
  public void setUserType(String userType) { this.userType = userType; }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPhoneCode() { return phoneCode; }
  public void setPhoneCode(String phoneCode) { this.phoneCode = phoneCode; }

  public String getEmailCode() { return emailCode; }
  public void setEmailCode(String emailCode) { this.emailCode = emailCode; }

  public Date getPhoneCodeUpdateTime() { return phoneCodeUpdateTime; }
  public void setPhoneCodeUpdateTime(Date phoneCodeUpdateTime) { this.phoneCodeUpdateTime = phoneCodeUpdateTime; }

  public Date getEmailCodeUpdateTime() { return emailCodeUpdateTime; }
  public void setEmailCodeUpdateTime(Date emailCodeUpdateTime) { this.emailCodeUpdateTime = emailCodeUpdateTime; }

  public String getLoginToken() { return loginToken; }
  public void setLoginToken(String loginToken) { this.loginToken = loginToken; }
}
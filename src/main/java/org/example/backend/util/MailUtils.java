package org.example.backend.util;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public final class MailUtils {

  @Value("${mail.user}")
  private String USER; // 发件人邮箱地址

  @Value("${mail.password}")
  private String PASSWORD; // 如果是QQ邮箱可以使用客户端授权码

  /**
   * 发送邮件
   * @param to 收件人邮箱
   * @param text 邮件正文
   * @param title 标题
   * @return 发送是否成功
   */
  public boolean sendMail(String to, String text, String title) {
    try {
      final Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.host", "smtp.qq.com");
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.starttls.enable", "true");

      Authenticator authenticator = new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(USER, PASSWORD);
        }
      };

      Session mailSession = Session.getInstance(props, authenticator);
      MimeMessage message = new MimeMessage(mailSession);
      message.setFrom(new InternetAddress(USER));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(title);
      message.setContent(text, "text/html;charset=UTF-8");

      // 确保调用 saveChanges 之前所有设置均已完成
      message.saveChanges();  // 确保状态被正确保存
      Transport.send(message);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}


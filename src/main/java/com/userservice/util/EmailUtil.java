package com.userservice.util;

//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
import com.userservice.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.subject}")
  private String SUBJECT;

  public void sendOtpEmail(String content, Users user, String verificationCode) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
    mimeMessageHelper.setTo(user.getEmail());
    String middleName = user.getMiddleName() == null || user.getMiddleName().equals("")
            ? "" : " " + user.getMiddleName();
    mimeMessageHelper.setSubject(user.getFirstName() + middleName + " " + user.getLastName() + " - " + SUBJECT);
    mimeMessageHelper.setText("<div><a href=\"http://localhost:9020/api/auth/verify?code=\""+verificationCode+"target=\"_blank\">click link to verify</a></div>"
            , true);
    javaMailSender.send(mimeMessage);
  }

}
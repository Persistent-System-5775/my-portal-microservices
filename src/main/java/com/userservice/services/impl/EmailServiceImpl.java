package com.userservice.services.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.userservice.constant.ConstantVariables;
import com.userservice.constant.ExceptionConstant;
import com.userservice.constant.StatusConstant;
import com.userservice.models.EmailLogger;
import com.userservice.models.Users;
import com.userservice.repository.EmailLoggerRepository;
import com.userservice.repository.UserRepository;
import com.userservice.util.CommonUtil;
import com.userservice.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.userservice.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String SENDER_EMAIL;

    @Value("${spring.mail.password}")
    private String PASSWORD;

    @Value("${spring.mail.subject}")
    private String SUBJECT;

    @Value("${spring.mail.host}")
    private String HOST;

    @Value("${spring.mail.port}")
    private String PORT;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String TLS_ENABLE;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String AUTH_ENABLE;

    @Value("${fileLocation}")
    private String PATH;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailLoggerRepository emailLoggerRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public boolean sendVerificationEmail(Users user, String verificationCode) {
        boolean foo = false; // Set the false, default variable "foo", we will allow it after sending code
        // process email

        Properties properties = new Properties();

//      Setup host and mail server
        properties.put("mail.smtp.auth", AUTH_ENABLE);
        properties.put("mail.smtp.starttls.enable", TLS_ENABLE);
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);


//	     get the session object and pass username and password
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, PASSWORD);
            }
        });

        try {
            String content = ConstantVariables.EMAIL_CONTENT;
            content = content.replace("[[name]]",
                    user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName());

            String verifyURL = CommonUtil.getSiteURL() + "/api/auth/verify?code=" + verificationCode;

            content = content.replace("[[URL]]", verifyURL.trim().toString());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html");
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setContent(multipart);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            String middleName = user.getMiddleName() == null || user.getMiddleName().equals("")
                    ? "" : " " + user.getMiddleName();
            message.setSubject(user.getFirstName() + middleName + " " + user.getLastName() + " - " + SUBJECT);
            message.setText(content, "UTF-8", "html");
            Transport.send(message);

            System.out.println("Email Sent With Inline Image Successfully to " + user.getEmail());

            foo = true;
            EmailLogger emailLogger = new EmailLogger();
            emailLogger.setErrorCode(ExceptionConstant.EMAIL_SENT_EC);
            emailLogger.setErrorDescription(ExceptionConstant.EMAIL_SENT_ED + " to <" + user.getEmail() + ">");
            emailLogger.setCreatedBy("sunilkmr5775");
            emailLogger.setCreatedDate(LocalDateTime.now());
            emailLoggerRepository.save(emailLogger);

        } catch (Exception e) {
            System.out.println("EmailService File Error" + e);
            EmailLogger emailLogger = new EmailLogger();
            emailLogger.setErrorCode(ExceptionConstant.EMAIL_NOT_SENT_EC);
            emailLogger.setErrorDescription(e.getMessage());
            emailLogger.setCreatedBy("sunilkmr5775");
            emailLogger.setCreatedDate(LocalDateTime.now());
            emailLoggerRepository.save(emailLogger);
        }
        return foo;
    }

    @Override
    public String verifyUser(String verificationCode) {
        String result = null;
        Users user = userRepository.findByVerificationCode(verificationCode);
        if (user == null) {
            result = "No user found for verification code: " + verificationCode;
        } else {
            if (user.isEmailVerified()) {
                return "Your account has already been verified.";
            }
            if (Duration.between(user.getVerificationCodeGeneratedTime(),
                    LocalDateTime.now()).getSeconds() < (30 * 60)) {
                if (user.getVerificationCode().equals(verificationCode) && !user.isEmailVerified()) {
                    user.setVerificationCode(null);
                    user.setStatus(StatusConstant.STATUS_ACTIVE);
                    user.setEmailVerified(true);
                    user.setVerificationCodeGeneratedTime(null);
                    userRepository.save(user);
                    result = "User account verified successfully";
                } else {
                    result = "Verification code didn't matched or User already verified";
                }
            } else {
                result = "Verification link expired";
            }
        }
        return result;

    }

    public String regenerateOtp(String email, String verificationCode) {
        Users user = (Users) userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        sendVerificationEmail(user, verificationCode);
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeGeneratedTime(LocalDateTime.now());
        user.setModifiedDate(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 30 minutes";
    }

}

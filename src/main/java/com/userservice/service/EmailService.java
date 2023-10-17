package com.userservice.service;

import com.userservice.models.Users;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	boolean sendVerificationEmail(Users user, String randomCode);

//	BaseResponse sendEmailNotification();
//	String verify(String code);

	String regenerateOtp(String email, String verificationCode);

	String verifyUser(String code);
}

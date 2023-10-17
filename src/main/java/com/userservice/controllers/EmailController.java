package com.userservice.controllers;

import com.userservice.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.userservice.service.EmailService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class EmailController {

	@Autowired
	EmailService emailService;

	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
		return emailService.verifyUser(code);
	}
	@PutMapping("/regenerate-otp")
	public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
		String verificationCode = RandomString.make(64);
		return new ResponseEntity<>(emailService.regenerateOtp(email, verificationCode), HttpStatus.OK);
	}

}

package com.userservice.request;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class SignupRequest {
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	@NotBlank
	@Size(max = 10)
	private String gender;

	@NotBlank
	@Size(max = 120)
	private String firstName;

	@Size(max = 120)
	private String middleName;

	@NotBlank
	@Size(max = 120)
	private String lastName;

	@NotBlank
	@Size(max = 120)
	private String contactNumber;

//	@NotBlank
	@Size(max = 10)
	private String dd;
	
//	@NotBlank
	@Size(max = 10)
	private String mm;
	
//	@NotBlank
	@Size(max = 10)
	private String yyyy;


////	@Column(nullable = false)
//	private MultipartFile file;


//	@NotBlank
	private String captcha;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}



//	public String getPanNumber() {
//		return panNumber;
//	}
//
//	public void setPanNumber(String panNumber) {
//		this.panNumber = panNumber;
//	}

	public String getDd() {
		return dd;
	}

	public void setDd(String dd) {
		this.dd = dd;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getYyyy() {
		return yyyy;
	}

	public void setYyyy(String yyyy) {
		this.yyyy = yyyy;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}


//	public MultipartFile getFile() {
//		return file;
//	}
//
//	public void setFile(MultipartFile file) {
//		this.file = file;
//	}

}

package com.userservice.controllers;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.userservice.constant.ExceptionConstant;
import com.userservice.jwt.JwtUtils;
import com.userservice.models.ERole;
import com.userservice.models.Role;
import com.userservice.models.Users;
import com.userservice.repository.RoleRepository;
import com.userservice.repository.UserRepository;
import com.userservice.request.LoginRequest;
import com.userservice.request.SignupRequest;
import com.userservice.response.BaseResponse;
import com.userservice.service.UserService;
import com.userservice.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.userservice.constant.StatusConstant;
import com.userservice.exception.NoDataFoundException;
import com.userservice.response.JwtResponse;
import com.userservice.response.MessageResponse;
import com.userservice.service.EmailService;
import com.userservice.services.impl.UserDetailsImpl;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Component
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private EmailService emailService;


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/createUser")
	public ResponseEntity<Users> createUser(@RequestBody SignupRequest signupRequest) {
		Users user1 = userService.createUser(signupRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(// @Valid
										  @RequestBody SignupRequest signUpRequest
										  // ,@RequestPart(value = "file" , required=true) MultipartFile file
	) throws NoDataFoundException, UnsupportedEncodingException, MessagingException
	{

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
        BaseResponse response = new BaseResponse();
		// Create new user's account
		Users user = new Users();
		user.setCaptcha("");
		user.setContactNumber(signUpRequest.getContactNumber());
		user.setCreatedBy("sunilkmr5775");
		user.setCreatedDate(LocalDateTime.now());
		user.setDateOfBirth(signUpRequest.getDd() + "-" + signUpRequest.getMm() + "-" + signUpRequest.getYyyy());
		user.setEmail(signUpRequest.getEmail());
		user.setFirstName(signUpRequest.getFirstName());
		user.setMiddleName(signUpRequest.getMiddleName());
		user.setLastName(signUpRequest.getLastName());
//		user.setPanNumber(signUpRequest.getPanNumber());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setStatus(StatusConstant.STATUS_INACTIVE);
		user.setGender(signUpRequest.getGender());
		user.setUsername(signUpRequest.getUsername());

		Set<String> strRoles = new HashSet();// signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		/*List<Role> strRoles = roleRepository.findAll();
		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_NORMAL)
					.orElseThrow(() -> new RuntimeException(ERole.ROLE_NORMAL + " Error: Role is not found in DB."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				if ("ROLE_ADMIN".equals(String.valueOf(role.getRoleName()))){// == null || "".equals(role.getRoleName()) ? "ROLE_NORMAL" : role.getRoleName()))) {
					Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN).orElseThrow(
							() -> new NoDataFoundException("Error:  Role " + role + " is not found in DB."));

					roles.add(adminRole);
				} else if ("ROLE_MODERATOR".equals(String.valueOf(role.getRoleName()))) {
					Role modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR).orElseThrow(
							() -> new NoDataFoundException("Error: Role " + role + " is not found in DB."));
					roles.add(modRole);
				} else {
					Role userRole = roleRepository.findByRoleName(ERole.ROLE_NORMAL).orElseThrow(
							() -> new NoDataFoundException("Error: Role " + role + " is not found in DB."));
					roles.add(userRole);
				}
			});
		}*/

		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_NORMAL)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			signUpRequest.getRole().forEach(role -> {
				switch (role) {
					case "ROLE_ADMIN":
						Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "ROLE_MODERATOR":
						Role modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					default:
						Role userRole = roleRepository.findByRoleName(ERole.ROLE_NORMAL)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);

		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setVerificationCodeGeneratedTime(LocalDateTime.now());
		user.setEmailVerified(false);

//		Long id = userRepository.save(user).getId();
		Users user1 = userRepository.save(user);

		emailService.sendVerificationEmail(user, randomCode);

//		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
		return ResponseEntity.ok(new MessageResponse("Username " + signUpRequest.getUsername() + " registered successfully!"));
	}

//	 Returns the details of current user
	@GetMapping(value = "/current-user")
	public UserDetails getCurrentUser(Principal principal) {
		try {
		System.out.println("username in controller: " + principal.getName());
		return  this.userDetailsService.loadUserByUsername(principal.getName());
		} catch(Exception ex) {
			System.out.println("Error occurred in getCurrentUser() method: "+ex.getMessage());
			throw new NullPointerException();
		}
	}
	@GetMapping("/")
	public List<Users> getAllUsers() {
		return userRepository.findAll();
	}
	@GetMapping("/activeUsers")
	public List<Users> getAllActiveUsers() {
		return userRepository.findAll()
		.stream().filter(users -> !users.isDeleted() &&
						users.getStatus().equals(StatusConstant.STATUS_ACTIVE))
				.collect(Collectors.toList());
	}
	@GetMapping("/inactiveUsers")
	public List<Users> getAllInactiveUsers() {
		return userRepository.findAll()
				.stream().filter(users -> !users.isDeleted()
				&& users.getStatus().equals(StatusConstant.STATUS_INACTIVE))
				.collect(Collectors.toList());
	}
	@GetMapping("/deletedUsers")
	public List<Users> getAllDeletedUsers() {
		return userRepository.findAll().stream()
				.filter(users -> users.isDeleted()).collect(Collectors.toList());
	}


	@DeleteMapping("/{userId}")
	public BaseResponse delete(@PathVariable Long userId) {
		BaseResponse baseResponse = new BaseResponse();
		Optional<Users> user = Optional.ofNullable(this.userRepository.findById(userId).orElseThrow(
				() -> new NoDataFoundException("Error: Role Id <" + userId + "> is not found in DB.")));
		try {
			if (user != null) {
				Users eUser = user.get();
				eUser.setDeleted(true);
				eUser.setStatus(StatusConstant.STATUS_INACTIVE);
				eUser.setModifiedBy("sunilkmr5775");
				eUser.setModifiedDate(LocalDateTime.now());
				this.userRepository.save(eUser);
				baseResponse.setStatus(StatusConstant.STATUS_SUCCESS);
				baseResponse.setErrorCode(ExceptionConstant.DATA_SAVED_SUCCESSFULLY_EC);
				baseResponse.setErrorDesc(ExceptionConstant.DATA_SAVED_SUCCESSFULLY_ED);

				return baseResponse;
			}
		} catch (Exception e) {
			baseResponse.setStatus(StatusConstant.STATUS_FAILURE);
			baseResponse.setErrorCode(ExceptionConstant.FILE_NOT_SAVED_EC);
			baseResponse.setErrorDesc(e.getMessage());
			return baseResponse;
		}
		return baseResponse;
	}

}

package com.userservice.services.impl;

import com.userservice.constant.StatusConstant;
import com.userservice.models.ERole;
import com.userservice.models.Role;
import com.userservice.models.Users;
import com.userservice.repository.RoleRepository;
import com.userservice.repository.UserRepository;
import com.userservice.request.SignupRequest;
import com.userservice.response.BaseResponse;
import com.userservice.response.MessageResponse;
import com.userservice.service.EmailService;
import com.userservice.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Users createUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return null;//ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return null;//ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
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
        userRepository.save(user);
        //Users user1 = userRepository.save(user);

        emailService.sendVerificationEmail(user, randomCode);

//		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
        return null;//ResponseEntity.ok(new MessageResponse("Username " + signUpRequest.getUsername() + " registered successfully!"));
    }
}

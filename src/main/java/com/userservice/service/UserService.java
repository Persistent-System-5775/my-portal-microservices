package com.userservice.service;

import com.userservice.models.Users;
import com.userservice.request.SignupRequest;

public interface UserService {
    Users createUser(SignupRequest signupRequest);
}

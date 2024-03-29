package com.userservice.services.impl;

import com.userservice.models.Users;
import com.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = userRepository.findByUsername(username);
    if(user==null) {
     //   .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    	throw new UsernameNotFoundException("User Not Found with username: " + username); 
    }
    return UserDetailsImpl.build(user);
  }

}

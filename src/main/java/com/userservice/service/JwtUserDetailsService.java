//package com.userservice.app.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.userservice.app.models.Users;
//import com.userservice.app.repository.UserRepository;
//
//@Service
//public class JwtUserDetailsService implements UserDetailsService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		System.out.println("username in jwt service: "+username);
//		Users users = this.userRepository.findByUsername(username);
//
//		if (users == null) {
//			System.out.println("User not found");
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//		System.out.println("username 3: "+users.getUsername());
//		return (UserDetails) users;
//	}
//
////	public User save(UserDto user) {
////		UserDao newUser = new UserDao();
////		newUser.setUsername(user.getUsername());
////		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
////		return userDao.save(newUser);
////	}
////
////	public List<UserDao> findAll() {
////		List<UserDao> userList = new ArrayList<>();
////		userList = userDao.findAll();
//////		for (UserDao user : userList) {
//////
//////		}
////		return userList;
////	}
//}
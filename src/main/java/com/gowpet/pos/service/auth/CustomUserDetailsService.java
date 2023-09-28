package com.gowpet.pos.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gowpet.pos.service.UserService;

public class CustomUserDetailsService implements UserDetailsService {
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var foundUser = userService.getUserByUsername(username);
		if (foundUser == null) {
			throw new UsernameNotFoundException("Username not found.");
		}
		
		return new CustomUserDetails(foundUser);
	}

}

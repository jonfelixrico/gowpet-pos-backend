package com.gowpet.pos.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gowpet.pos.service.UserService;

@Service
class ImplUserDetailsService implements UserDetailsService {
	private UserService userService;

	ImplUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userService.getUserByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Username %s is not found", username));
		}
		
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(new SimpleGrantedAuthority("USER"))
				.build();
	}	
}

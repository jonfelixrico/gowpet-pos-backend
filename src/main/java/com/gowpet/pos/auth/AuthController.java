package com.gowpet.pos.auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AuthController {
	private JwtService jwtService;
	private AuthenticationManager authManager;
	
	AuthController(JwtService jwtService, AuthenticationManager authManager) {
		this.jwtService = jwtService;
		this.authManager = authManager;
	}
	
	@PostMapping("/authenticate")
	String authenticate(@RequestBody AuthenticateDto dto) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(
				dto.getUsername(),
				dto.getPassword(),
				List.of(new SimpleGrantedAuthority("USER"))));

		if (!auth.isAuthenticated()) {
			throw new UsernameNotFoundException("Wrong credentials");
		}
		
		return jwtService.generateToken(auth.getName());
	}
}

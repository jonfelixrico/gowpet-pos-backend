package com.gowpet.pos.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
	private AuthenticationManager authManager;
	
	@PostMapping
	public ResponseEntity<String> authenticateUser (@RequestBody LoginRequestDto credentials) {
		Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<String>("User login successfully!...", HttpStatus.OK);
	}
	
	@GetMapping
	public void getSession () {
		//	TODO remove this
	}
}

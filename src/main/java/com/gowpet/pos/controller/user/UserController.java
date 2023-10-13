package com.gowpet.pos.controller.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private PasswordEncoder pwEncoder;
	private UserService userService;

	UserController(PasswordEncoder pwEncoder, UserService userService) {
		this.pwEncoder = pwEncoder;
		this.userService = userService;
	}

	/**
	 * WARNING: This is for debugging only. This should be disabled in production.
	 * @param dto
	 */
	@PostMapping
	public void createUser (@RequestBody CreateUserDto dto) {
		userService.createUser(dto.getUsername(), pwEncoder.encode(dto.getPassword()));
	}
}

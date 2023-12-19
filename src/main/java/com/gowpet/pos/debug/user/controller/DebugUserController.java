package com.gowpet.pos.debug.user.controller;

import com.gowpet.pos.user.controller.CreateUserDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.user.service.UserService;

@RestController
@RequestMapping("/debug/user")
@ConditionalOnExpression("${app.debug:false}")
public class DebugUserController {
	private PasswordEncoder pwEncoder;
	private UserService userService;

	DebugUserController(PasswordEncoder pwEncoder, UserService userService) {
		this.pwEncoder = pwEncoder;
		this.userService = userService;
	}

	@PostMapping
	public void createUser (@RequestBody CreateUserDto dto) {
		userService.create(dto.getUsername(), pwEncoder.encode(dto.getPassword()));
	}
}

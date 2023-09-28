package com.gowpet.pos.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
	@PostMapping
	public void authenticateUser (@RequestBody LoginRequestDto credentials) {
		
	}
}

package com.gowpet.pos.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

import com.gowpet.pos.user.service.User;
import com.gowpet.pos.user.service.UserService;

@WebMvcTest(controllers = AuthController.class)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void AuthController_Authenticate_ThrowsError() throws Exception {
		given(userService.getUserByUsername(ArgumentMatchers.any())).willAnswer(User.builder());
		var request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"user\", \"password\": \"password\" }");
		mockMvc.perform(request);
	}
	
	@Test
	public void AuthController_Authenticate_ReturnsToken() throws Exception {
	}
}

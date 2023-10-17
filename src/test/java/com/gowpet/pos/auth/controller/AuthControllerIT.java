package com.gowpet.pos.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

import com.gowpet.pos.user.service.User;
import com.gowpet.pos.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIT {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void AuthController_AuthenticateWithWrongCredentials_ThrowsError() throws Exception {
		given(userService.getUserByUsername(ArgumentMatchers.any())).willAnswer(answer -> null);

		var request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"user\", \"password\": \"wrongpassword\" }");

		mockMvc.perform(request).andExpect(status().is4xxClientError());
	}
	
	@Test
	void AuthController_AuthenticateWithCorrectCredentials_ReturnsToken() throws Exception {
		given(userService.getUserByUsername(ArgumentMatchers.any()))
			.willAnswer(answer -> User.builder()
					.username("user1")
					.password(passwordEncoder.encode("password"))
					.build());

		var request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"user\", \"password\": \"password\" }");

		mockMvc.perform(request).andExpect(status().isOk());
	}
}

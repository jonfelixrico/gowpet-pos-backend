package com.gowpet.pos.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

import com.gowpet.pos.user.service.User;
import com.gowpet.pos.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void AuthController_Authenticate_ThrowsError() throws Exception {
	}
	
	@Test
	void AuthController_Authenticate_ReturnsToken() throws Exception {
		given(userService.getUserByUsername(ArgumentMatchers.any())).willAnswer(answer -> new User("user1", passwordEncoder.encode("password")));

		var request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"user\", \"password\": \"password\" }");
		mockMvc.perform(request).andExpect(status().isOk());
	}
}

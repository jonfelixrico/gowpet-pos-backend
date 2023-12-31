package com.gowpet.pos.integrationtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



@SpringBootTest
@AutoConfigureMockMvc
class AuthIT {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void AuthController_AuthenticateWithWrongCredentials_ThrowsError() throws Exception {
		var request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"username": "user1",
							"password": "wrongpassword"
						}
						""");

		mockMvc.perform(request).andExpect(status().is4xxClientError());
	}
	
	@Test
	void AuthController_AuthenticateWithCorrectCredentials_ReturnsToken() throws Exception {
		var request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"username": "user1",
							"password": "password"
						}
						""");

		mockMvc.perform(request).andExpect(status().isOk());
	}
}

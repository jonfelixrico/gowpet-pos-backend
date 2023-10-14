package com.gowpet.pos.auth.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AuthController.class)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {
	@Autowired
	private MockMvc mockMvc;
}

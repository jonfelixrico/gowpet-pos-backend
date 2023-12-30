package com.gowpet.pos.integrationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("user1")
public class UserIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void UserController_CreateUser_Succeeds() throws Exception {
        var username = String.format("user-%d", Instant.now().toEpochMilli());
        var request = post("/user").contentType(MediaType.APPLICATION_JSON).content(String.format("""
                    {
                        "username": "%s",
                        "password": "password"
                    }
                """, username));
        mockMvc.perform(request).andExpect(status().isOk());
    }
}

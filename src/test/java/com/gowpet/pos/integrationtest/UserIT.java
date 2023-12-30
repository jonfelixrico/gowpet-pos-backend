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
        var body = String.format("""
                    {
                        "username": "user-%s",
                        "password": "password"
                    }
                """, Instant.now().toEpochMilli());
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());

        /*
         * The intention here is to verify that the user really has been created.
         *
         * TODO once we get a /user/{id} route, use that instead of authenticating again.
         *  Checking the existence using /authenticate is weird since there is a current "session" with user1
         */
        mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }
}

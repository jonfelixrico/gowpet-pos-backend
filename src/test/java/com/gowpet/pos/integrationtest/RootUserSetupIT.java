package com.gowpet.pos.integrationtest;

import com.gowpet.pos.user.service.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RootUserSetupIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void RootUserSetUp_CreateRoot_ThrowsError() throws Exception {
        mockMvc.perform(post("/user/root")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                	"username": "new-root",
                                	"password": "password"
                                }
                                """))
                .andExpect(status().is5xxServerError());
    }
}

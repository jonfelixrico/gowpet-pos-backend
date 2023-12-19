package com.gowpet.pos.integrationtest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
/*
    This is to just prevent the DML that populates the test DB from running.
    In this test suite, we want to simulate a newly-deployed environment.
 */
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=update"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RootUserSetupIT {
    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    void RootUserSetUp_CreateRoot_CanLogin() throws Exception {
        mockMvc.perform(post("/user/root").contentType(MediaType.APPLICATION_JSON).content("""
                {
                	"username": "new-root",
                	"password": "password"
                }
                """)).andExpect(status().isOk());

        var request = post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        	"username": "new-root",
                        	"password": "password"
                        }
                        """);
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void RootUserSetUp_CreateRoot_ThrowsError() throws Exception {
        mockMvc.perform(post("/user/root")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                	"username": "another-root",
                                	"password": "password"
                                }
                                """))
                .andExpect(status().isConflict());
    }
}

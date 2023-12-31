package com.gowpet.pos.integrationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void UserController_CreateUsers_ShowsInList() throws Exception {
        for (int i = 0; i < 90; i++) {
            // "aaa" was used to make sure that those newly-created users are shown at the top of the list
            var body = String.format("""
                        {
                            "username": "aaa-%03d",
                            "password": "password"
                        }
                    """, i);

            mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(body))
                    .andExpect(status().isOk());
        }

        // get all 90 records that we just created
        mockMvc.perform(get("/user").queryParam("itemCount", "15").queryParam("pageNo", "0"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user").queryParam("itemCount", "15").queryParam("pageNo", "1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user").queryParam("itemCount", "15").queryParam("pageNo", "2"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user").queryParam("itemCount", "15").queryParam("pageNo", "3"))
                .andExpect(status().isOk());

        // get other records (i.e. those in import.sql)
        mockMvc.perform(get("/user").queryParam("itemCount", "15").queryParam("pageNo", "4"))
                .andExpect(status().isOk());
    }
}

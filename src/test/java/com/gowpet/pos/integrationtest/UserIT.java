package com.gowpet.pos.integrationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        // Test if the total count is correct
        var firstPage = mockMvc.perform(get("/user").queryParam("itemCount", "15").queryParam("pageNo", "0"))
                .andExpect(status().isOk());
        var totalCount = Integer.parseInt(Objects.requireNonNull(firstPage.andReturn().getResponse().getHeader("X-Total-Count")));
        assertThat(totalCount).isGreaterThanOrEqualTo(90 / 15); // 90 items divided by pages containing 15 items each

        //  Now test if the content per page is correct
        for (int pageNo = 0; pageNo < 90 / 15; pageNo++) {
            var request = get("/user").queryParam("itemCount", "15").queryParam("pageNo", Integer.toString(pageNo));
            var result = mockMvc.perform(request).andExpect(status().isOk());

            var startingNo = 15 * pageNo;
            for (int recordNo = startingNo; recordNo < startingNo + 15; recordNo++) {
                var path = String.format("$[?(@.username == 'aaa-%03d')]", recordNo);
                result.andExpect(jsonPath(path).exists());
            }
        }
    }
}

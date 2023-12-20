package com.gowpet.pos.integrationtest;

import com.gowpet.pos.user.service.RootUserSetupService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RootUserSetupIT {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private RootUserSetupService rootUserSvc;

    @Order(1)
    @Test
    void RootUserSetUp_CreateRoot_CanLogin() throws Exception {
        when(rootUserSvc.hasRootUserBeenSetUp()).thenReturn(false);

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

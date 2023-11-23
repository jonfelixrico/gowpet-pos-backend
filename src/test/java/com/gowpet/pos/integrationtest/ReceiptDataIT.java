package com.gowpet.pos.integrationtest;

import com.gowpet.pos.billing.receipt.ReceiptDataService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReceiptDataIT {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ReceiptDataService receiptDataSvcSpy;

    @Test
    void ReceiptData_GetUnset_ReturnsNull() throws Exception {
        when(receiptDataSvcSpy.getReceiptData()).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/billing/receipt"))
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @Order(1)
    void ReceiptData_Get_ReturnsData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/billing/receipt"))
                .andExpect(jsonPath("$.header").value("test header"))
                .andExpect(jsonPath("$.address").value("test address"))
                .andExpect(jsonPath("$.contactNo").value("0920-123-4567"))
                .andExpect(jsonPath("$.snsLink").value("http://test-sns"))
                .andExpect(jsonPath("$.snsMessage").value("follow us"));
    }

    @Test
    @Order(2)
    void ReceiptData_Update_ReturnsData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/billing/receipt")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "header": "test header edited",
                            "address": "test address",
                            "contactNo": "0920-123-4567",
                            "snsLink": "http://test-sns",
                            "snsMessage": "follow us"
                        }
                        """))
                        .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/billing/receipt"))
                .andExpect(jsonPath("$.header").value("test header edited"))
                .andExpect(jsonPath("$.address").value("test address"))
                .andExpect(jsonPath("$.contactNo").value("0920-123-4567"))
                .andExpect(jsonPath("$.snsLink").value("http://test-sns"))
                .andExpect(jsonPath("$.snsMessage").value("follow us"));
    }
}

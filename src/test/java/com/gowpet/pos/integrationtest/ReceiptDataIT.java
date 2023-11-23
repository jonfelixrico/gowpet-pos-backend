package com.gowpet.pos.integrationtest;

import com.gowpet.pos.billing.receipt.ReceiptDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1")
public class ReceiptDataIT {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ReceiptDataService receiptDataSvcSpy;

    @Test
    void ReceiptData_Get_ReturnsNull() throws Exception {
        when(receiptDataSvcSpy.getReceiptData()).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/billing/receipt"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }
}

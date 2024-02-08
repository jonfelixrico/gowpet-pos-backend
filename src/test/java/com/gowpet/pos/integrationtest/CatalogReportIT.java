package com.gowpet.pos.integrationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1")
public class CatalogReportIT {
    @Autowired
    private MockMvc mockMvc;

    @BeforeTestClass
    void createTestData() {
        // TODO create test data
    }

    @Test
    void Reporting_RetrieveAllTime_ReturnsReport() {
        // TODO add test
    }
}

package com.gowpet.pos.integrationtest;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1")
public class CatalogReportIT {
    @Autowired
    private MockMvc mockMvc;

    private String createCatalogItem(String name, Double price) throws Exception {
        var createReq = post("/catalog/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
						{
							"name": "%s",
							"price": %f
						}
						
						""", name, price));
        var serializedJson = mockMvc.perform(createReq)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return JsonPath.read(serializedJson, "$.id");
    }

    @BeforeTestClass
    void createTestData() {
        // TODO create test data
    }

    @Test
    void Reporting_RetrieveAllTime_ReturnsReport() {
        // TODO add test
    }
}

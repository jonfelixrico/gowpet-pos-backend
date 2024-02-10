package com.gowpet.pos.integrationtest;

import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1")
public class CatalogReportIT {
    @Autowired
    private MockMvc mockMvc;
    private boolean hasBeenSetUp = false;
    private String[] testItemIds = new String[]{};

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

    private void createBilling(String item1Id, String item2Id, String item3Id, int quantityEach) throws Exception {
        var postReq = post("/billing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                        	"items": [
                        		{
                        			"catalogId": "%s",
                        			"quantity": %d
                        		},
                        		{
                        			"catalogId": "%s",
                        			"quantity": %d
                        		},
                        		{
                        			"catalogId": "%s",
                        			"quantity": %d
                        		}
                        	]
                        }
                        """, item1Id, quantityEach, item2Id, quantityEach, item3Id, quantityEach));

        mockMvc.perform(postReq)
                .andExpect(status().isOk());
    }

    /*
     * Didn't go for BeforeAll since we'd have to use a static method for it.
     * Adjusting the seed code to be static is going to be a PITA so we're going for this
     * instead to keep the MockMvc approach.
     */
    @BeforeEach
    void createTestData() throws Exception {
        // This is to make this BeforeEach run like BeforeAll
        if (hasBeenSetUp) {
            return;
        }

        testItemIds = new String[]{
                createCatalogItem("report-item-1", 10.0),
                createCatalogItem("report-item-2", 20.0),
                createCatalogItem("report-item-3", 30.0)
        };

        for (int i = 1; i <= 10; i++) {
            createBilling(testItemIds[0], testItemIds[1], testItemIds[2], i);
        }

        hasBeenSetUp = true;
    }

    @Test
    void Reporting_RetrieveAllTime_ReturnsReport() throws Exception {
        var getReq = get("/catalog/report");

        mockMvc.perform(getReq)
                .andExpectAll(
                        status().isOk(),
                        jsonPath(String.format("$.entries[?(@.catalogItemId == '%s')].price", testItemIds[0])).value(10.0),
                        jsonPath(String.format("$.entries[?(@.catalogItemId == '%s')].quantity", testItemIds[0])).value(55.0),
                        jsonPath(String.format("$.entries[?(@.catalogItemId == '%s')].price", testItemIds[1])).value(20.0),
                        jsonPath(String.format("$.entries[?(@.catalogItemId == '%s')].quantity", testItemIds[1])).value(55.0),
                        jsonPath(String.format("$.entries[?(@.catalogItemId == '%s')].price", testItemIds[2])).value(30.0),
                        jsonPath(String.format("$.entries[?(@.catalogItemId == '%s')].quantity", testItemIds[2])).value(55.0)
                );
    }
}

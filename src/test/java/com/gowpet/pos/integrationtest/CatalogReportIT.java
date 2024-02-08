package com.gowpet.pos.integrationtest;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    private void createBilling(String item1Id, String item2Id, String item3Id, Integer quantityEach) throws Exception {
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

    private boolean hasBeenSetUp = false;

    /**
     * Didn't go for BeforeAll since we'd have to use a static method for it.
     * Adjusting the seed code to be static is going to be a PITA so we're going for this
     * instead to keep the MockMvc approach.
     *
     * @throws Exception
     */
    @BeforeEach
    void createTestData() throws Exception {
        // This is to make this BeforeEach run like BeforeAll
        if (hasBeenSetUp) {
            return;
        }

        var itemId1 = createCatalogItem("report-item-1", 10.0);
		var itemId2 = createCatalogItem("report-item-2", 20.0);
		var itemId3 = createCatalogItem("report-item-3", 30.0);

		for (int i = 1; i <= 10; i++) {
			createBilling(itemId1, itemId2, itemId3, i);
		}

        hasBeenSetUp = true;
    }

    @Test
    void Reporting_RetrieveAllTime_ReturnsReport() {
        // TODO add test
    }
}

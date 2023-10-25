package com.gowpet.pos.billing.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1")
class BillingControllerIT {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void BillingController_AccessNonexistentRecord_ThrowError() throws Exception {
		mockMvc.perform(get("/billing/non-existent"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	void BillingController_Create_CanAccessRecord() throws Exception {
		var postReq = post("/billing")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"items": [
								{
									"catalogId": "3e2d537a-3b2a-476d-804b-9ab4c4556cbf",
									"price": 120.00,
									"quantity": 3.0
								}
							],
							"amountOverride": null,
							"notes": null
						}
						""");
		
		mockMvc.perform(postReq)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.items[0].item.id").value("3e2d537a-3b2a-476d-804b-9ab4c4556cbf"))
			.andExpect(jsonPath("$.items[0].price").value(120.00))
			.andExpect(jsonPath("$.items[0].quantity").value(3.0));
	}
	
	@Test
	void BillingController_DeleteBilling_CannotAccessRecord() throws Exception {
		var postReq = post("/billing")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"items": [
								{
									"catalogId": "3e2d537a-3b2a-476d-804b-9ab4c4556cbf",
									"price": 120.00,
									"quantity": 3.0
								}
							],
							"amountOverride": null,
							"notes": null
						}
						""");
		
		var serializedJson = mockMvc.perform(postReq)
			.andReturn()
			.getResponse()
			.getContentAsString();
		var id = JsonPath.read(serializedJson, "$.id");
		var route = String.format("/billing/%s", id);
		
		mockMvc.perform(get(route))
			.andExpect(status().isOk());
		
		mockMvc.perform(delete(route))
			.andExpect(status().isOk());
		
		mockMvc.perform(get(route))
			.andExpect(status().isNotFound());
	}
}

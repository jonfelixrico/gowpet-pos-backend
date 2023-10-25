package com.gowpet.pos.billing.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
	void BillingController_Create_ReturnsCreatedValue() throws Exception {
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
							"notes": "This is the create test"
						}
						""");
		
		mockMvc.perform(postReq)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.items[0].catalogItem.id").value("3e2d537a-3b2a-476d-804b-9ab4c4556cbf"))
			.andExpect(jsonPath("$.items[0].price").value(120.00))
			.andExpect(jsonPath("$.items[0].quantity").value(3.0))
			.andExpect(jsonPath("$.notes").value("This is the create test"))
			.andExpect(jsonPath("$.amountOverride").isEmpty());
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
	
	@Test
	void BillingController_Update_UpdatesReflected() throws Exception {
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
		
		var putReq = put(route)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"items": [
								{
									"catalogId": "002a95ff-00b1-48ee-98ce-6469a076d201",
									"price": 50.00,
									"quantity": 1.0
								},
								{
									"catalogId": "3e2d537a-3b2a-476d-804b-9ab4c4556cbf",
									"price": 50.00,
									"quantity": 5.0
								}
							],
							"amountOverride": 1000.00,
							"notes": "This is intentionally overpriced"
						}
						""");

		mockMvc.perform(putReq)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.items[0].catalogItem.id").value("002a95ff-00b1-48ee-98ce-6469a076d201"))
			.andExpect(jsonPath("$.items[0].price").value(50.00))
			.andExpect(jsonPath("$.items[0].quantity").value(1.0))
			.andExpect(jsonPath("$.items[1].catalogItem.id").value("3e2d537a-3b2a-476d-804b-9ab4c4556cbf"))
			.andExpect(jsonPath("$.items[1].price").value(50.00))
			.andExpect(jsonPath("$.items[1].quantity").value(5.0))
			.andExpect(jsonPath("$.amountOverride").value(1000.0))
			.andExpect(jsonPath("$.notes").value("This is intentionally overpriced"));
	}
	
	@Test
	void BillingController_Create_ShowsInList() throws Exception {
		mockMvc.perform(post("/billing")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"items": [],
							"amountOverride": null,
							"notes": "Billing 1"
						}
						"""));
		
		mockMvc.perform(post("/billing")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"items": [],
							"amountOverride": null,
							"notes": "Billing 2"
						}
						"""));
		
		mockMvc.perform(post("/billing")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"items": [],
							"amountOverride": null,
							"notes": "Billing 3"
						}
						"""));
		
		mockMvc.perform(get("/billing"))
			.andExpect(jsonPath("$[?(@.notes=='Billing 1')]").exists())
			.andExpect(jsonPath("$[?(@.notes=='Billing 2')]").exists())
			.andExpect(jsonPath("$[?(@.notes=='Billing 3')]").exists());
	}
}

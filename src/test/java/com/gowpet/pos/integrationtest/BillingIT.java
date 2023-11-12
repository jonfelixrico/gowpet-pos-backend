package com.gowpet.pos.integrationtest;

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


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1")
class BillingIT {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void BillingController_AccessNonexistentRecord_ThrowError() throws Exception {
		mockMvc.perform(get("/billing/non-existent"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	void BillingController_CreateBasic_ReturnsCreatedValue() throws Exception {
		var postReq = post("/billing")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"items": [
								{
									"catalogId": "3e2d537a-3b2a-476d-804b-9ab4c4556cbf",
									"quantity": 3
								}
							]
						}
						""");
		
		mockMvc.perform(postReq)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.items[0].catalogItem.id").value("3e2d537a-3b2a-476d-804b-9ab4c4556cbf"))
			// The price is defined in import.sql. Just look for the insert statement associated with the id.
			.andExpect(jsonPath("$.items[0].price").value(40))
			.andExpect(jsonPath("$.items[0].quantity").value(3));
	}
	
	@Test
	void BillingController_CreateWithNotes_ReturnsCreatedValue() throws Exception {
		var postReq = post("/billing")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"items": [
								{
									"catalogId": "3e2d537a-3b2a-476d-804b-9ab4c4556cbf",
									"quantity": 3,
									"notes": "Item note"
								}
							],
							"notes": "Billing note"
						}
						""");
		
		mockMvc.perform(postReq)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.items[0].notes").value("Item note"))
			.andExpect(jsonPath("$.notes").value("Billing note"));
	}
	
	@Test
	void BillingController_CreateWithPriceOverride_ReturnsCreatedValue() throws Exception {
		var postReq = post("/billing")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"items": [
								{
									"catalogId": "3e2d537a-3b2a-476d-804b-9ab4c4556cbf",
									"quantity": 3
								},
								{
									"catalogId": "002a95ff-00b1-48ee-98ce-6469a076d201",
									"quantity": 1,
									"priceOverride": 50
								}
							]
						}
						""");
		
		mockMvc.perform(postReq)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.items[0].priceOverride").doesNotExist())
			.andExpect(jsonPath("$.items[1].priceOverride").value(50));
	}

	private String generateNote(int number) {
		return String.format("BillingController_Create_ShowsInList %d", number);
	}
	
	@Test
	void BillingController_Create_ShowsInList() throws Exception {
		for (int i = 0; i < 100; i++) {
			mockMvc.perform(post("/billing")
					.contentType(MediaType.APPLICATION_JSON)
					.content(String.format("""
						{
							"items": [],
							"notes": "%s"
						}
						""", generateNote(i))));
		}

		var result = mockMvc.perform(get("/billing?pageNo=0&itemCount=500"));

		for (int i = 0; i < 100; i++) {
			var path = String.format("$[?(@.notes=='%s')]", generateNote(i));
			result.andExpect(jsonPath(path).exists());
		}
	}
}

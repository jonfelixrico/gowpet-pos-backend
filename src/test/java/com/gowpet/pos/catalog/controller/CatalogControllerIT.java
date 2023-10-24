package com.gowpet.pos.catalog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1")
class CatalogControllerIT {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void CatalogController_CreateItem_ShowsInList () throws Exception {
		var createReq = post("/catalog/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"name": "test product",
							"price": 69.00
						}
						
						""");
		mockMvc.perform(createReq)
			.andExpect(status().isOk());
		
		mockMvc.perform(get("/catalog"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value("test product"))
			.andExpect(jsonPath("$[0].price").value(69.00));
	}
	
	@Test
	@WithMockUser(username = "user1")
	void CatalogController_DeleteItem_Succeeds () throws Exception {
		var createReq = post("/catalog/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"name": "product to delete",
							"price": 69.00
						}
						""");
		var serializedJson = mockMvc.perform(createReq)
			.andExpect(status().isOk())
			.andReturn()
			.getResponse().getContentAsString();
		var id = JsonPath.read(serializedJson, "$.id");
		
		mockMvc.perform(get(String.format("/catalog/product/%s", id)))
			.andExpect(status().isOk());
		
		mockMvc.perform(delete(String.format("/catalog/product/%s", id)))
			.andExpect(status().isOk());
		
		mockMvc.perform(get(String.format("/catalog/product/%s", id)))
			.andExpect(status().isNotFound());
	}
	
	@Test
	void CatalogController_UpdateItemName_Succeeds () throws Exception {
		var createReq = post("/catalog/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"name": "product to update",
							"price": 69.00
						}
						""");
		var serializedJson = mockMvc.perform(createReq)
			.andExpect(status().isOk())
			.andReturn()
			.getResponse().getContentAsString();
		var id = JsonPath.read(serializedJson, "$.id");
		
		var updateReq = put(String.format("/catalog/product/%s", id))
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"name": "updated name",
							"price": 69.00
						}
						""");
		mockMvc.perform(updateReq)
			.andExpect(status().isOk());
		
		mockMvc.perform(get(String.format("/catalog/product/%s", id)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("updated name"))
			.andExpect(jsonPath("$.price").value(69.00));
	}
}

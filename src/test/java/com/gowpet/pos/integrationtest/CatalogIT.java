package com.gowpet.pos.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user1")
class CatalogIT {
	@Autowired
	private MockMvc mockMvc;
	
	private String createItem(String name, Double price) throws UnsupportedEncodingException, Exception {
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

	@Test
	void CatalogController_CreateItem_CanAccessDetails () throws Exception {
		var id = createItem("test product", 69.00);
		
		mockMvc.perform(get(String.format("/catalog/product/%s", id)))
			.andExpect(status().isOk());
	}
	
	@Test
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
	
	@Test
	void CatalogController_CreateItems_AppearsOnList() throws Exception {
		var baseName = Instant.now().toEpochMilli();
		for (int i = 0; i < 50; i++) {
			createItem(String.format("list test %d", baseName + i), 123.45);
		}
		
		var body = mockMvc.perform(get("/catalog?itemCount=999"))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();
		
		List<String> ids = JsonPath.read(body, "$[?(@.name =~ /list test \\d+/)].id");
		assertEquals(50, ids.size());
	}
	
	@Test
	void CatalogController_CreateItems_AppearsExclusivelyInFilteredList() throws Exception {
		var baseName = Instant.now().toEpochMilli();
		for (int i = 0; i < 50; i++) {
			createItem(String.format("filter-test %d", baseName + i), 123.45);
		}
		
		var body = mockMvc.perform(get("/catalog?itemCount=999&searchTerm=filter-test"))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();
		
		List<String> ids = JsonPath.read(body, "$[*].id");
		assertEquals(50, ids.size());
	}
	
	@Test
	void CatalogController_SearchWithoutQueryParams_DoesNotThrow() throws Exception {
		mockMvc.perform(get("/catalog"))
			.andExpect(status().isOk());
	}
}

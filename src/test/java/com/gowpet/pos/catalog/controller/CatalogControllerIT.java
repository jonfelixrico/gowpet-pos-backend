package com.gowpet.pos.catalog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CatalogControllerIT {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void CatalogController_CreateItem_ShowsInList () throws Exception {
		var createReq = post("/catalog/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						[
							{
								"name": "test product",
								"price": 69.00
							}
						]
						""");
		mockMvc.perform(createReq)
			.andExpect(status().isOk());
		
		mockMvc.perform(get("/catalog"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value("test product"))
			.andExpect(jsonPath("$[0].price").value(69.00));
	}
}

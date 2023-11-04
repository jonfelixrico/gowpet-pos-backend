package com.gowpet.pos.catalog.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.catalog.CatalogItem;
import com.gowpet.pos.catalog.CatalogItemService;
import com.gowpet.pos.catalog.CatalogItemService.InsertFields;
import com.gowpet.pos.catalog.CatalogItemService.UpdateableFields;
import com.gowpet.pos.catalog.ItemType;
import com.gowpet.pos.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/catalog")
@SecurityRequirement(name = "bearerAuth")
class CatalogController {
	private CatalogItemService catalogSvc;
	private UserService userSvc;

	CatalogController(CatalogItemService catalogSvc, UserService userSvc) {
		this.catalogSvc = catalogSvc;
		this.userSvc = userSvc;
	}

	@GetMapping
	ResponseEntity<List<CatalogItem>> listItems(@RequestParam String searchTerm,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "30") Integer itemCount,
			@RequestParam List<ItemType> types) {
		var page = catalogSvc.list(pageNo, itemCount, types, searchTerm);
		
		var response = ResponseEntity.ok();
		if (pageNo == 0) {
			response = response.header("X-Total-Count", Integer.toString(page.getTotalPages()));
		}
		
		return response.body(page.getContent());
	}
	
	@PostMapping("/product")
	Map<String, String> createGoods(@RequestBody InsertFields item, @AuthenticationPrincipal UserDetails user) {
		var created = catalogSvc.create(item, userSvc.findByUsername(user.getUsername()));
		
		return Map.of("id", created.getId());
	}
	
	@GetMapping("/product/{id}")
	CatalogItem getProduct(@PathVariable String id) {
		return catalogSvc.get(id);
	}
	
	@DeleteMapping("/product/{id}")
	void deleteProduct(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
		catalogSvc.delete(id, userSvc.findByUsername(user.getUsername()));
	}
	
	@PutMapping("/product/{id}")
	CatalogItem updateProduct(@PathVariable String id, @RequestBody UpdateableFields item, @AuthenticationPrincipal UserDetails user) {
		return catalogSvc.update(id, item, userSvc.findByUsername(user.getUsername()));
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	ResponseEntity<ErrorResponse> handleNoSuchElement() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}

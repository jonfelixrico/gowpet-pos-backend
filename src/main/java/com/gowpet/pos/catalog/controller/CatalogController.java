package com.gowpet.pos.catalog.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import jakarta.validation.constraints.NotBlank;
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
import com.gowpet.pos.catalog.ItemType;
import com.gowpet.pos.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/catalog")
@SecurityRequirement(name = "bearerAuth")
class CatalogController {
	private final CatalogItemService catalogSvc;
	private final UserService userSvc;

	CatalogController(CatalogItemService catalogSvc, UserService userSvc) {
		this.catalogSvc = catalogSvc;
		this.userSvc = userSvc;
	}

	@GetMapping
	ResponseEntity<List<CatalogItem>> listItems(@RequestParam(required = false) String searchTerm,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "30") Integer itemCount,
			@RequestParam(required = false) List<ItemType> types) {
		var page = catalogSvc.list(pageNo, itemCount, types, searchTerm);
		return ResponseEntity.ok()
				.header("X-Total-Count", Integer.toString(page.getTotalPages()))
				.body(page.getContent());
	}
	
	@PostMapping("/product")
	Map<String, String> createGoods(@RequestBody CatalogItemService.CatalogItemFields item, @AuthenticationPrincipal UserDetails user) {
		var created = catalogSvc.create(item, userSvc.findByUsername(user.getUsername()));
		
		return Map.of("id", created.getId());
	}
	
	@GetMapping("/product/{id}")
	CatalogItem getProduct(@PathVariable String id) {
		return catalogSvc.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("/product/{id}")
	void deleteProduct(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
		catalogSvc.delete(id, userSvc.findByUsername(user.getUsername()));
	}
	
	@PutMapping("/product/{id}")
	CatalogItem updateProduct(@PathVariable String id, @RequestBody CatalogItemService.CatalogItemFields item, @AuthenticationPrincipal UserDetails user) {
		return catalogSvc.update(id, item, userSvc.findByUsername(user.getUsername()));
	}

	@GetMapping("/code/{code}")
	CatalogItem getProductByCode(@PathVariable @NotBlank String code) {
		return catalogSvc.findByCode(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@ExceptionHandler(NoSuchElementException.class)
	ResponseEntity<ErrorResponse> handleNoSuchElement() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}

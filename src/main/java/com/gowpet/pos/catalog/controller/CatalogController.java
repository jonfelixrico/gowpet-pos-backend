package com.gowpet.pos.catalog.controller;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.catalog.CatalogItem;
import com.gowpet.pos.catalog.CatalogItemService;
import com.gowpet.pos.catalog.CatalogItemService.UpdateableFields;
import com.gowpet.pos.catalog.ItemType;
import com.gowpet.pos.user.service.UserService;

@RestController
@RequestMapping("/catalog")
class CatalogController {
	private CatalogItemService catalogSvc;
	private UserService userSvc;

	CatalogController(CatalogItemService catalogSvc, UserService userSvc) {
		this.catalogSvc = catalogSvc;
		this.userSvc = userSvc;
	}

	@GetMapping
	List<CatalogItem> listItems() {
		return catalogSvc.listAll();
	}
	
	@PostMapping("/product")
	Map<String, String> createGoods(@RequestBody CreateProductDto item, @AuthenticationPrincipal UserDetails user) {
		// TODO move logic to the service level
		var userRecord = userSvc.findByUsername(user.getUsername());
		var now = Instant.now();

		var toCreate = CatalogItem.builder()
				.name(item.getName())
				.price(item.getPrice())
				.type(ItemType.PRODUCT)
				.createDt(now)
				.createBy(userRecord)
				.updateDt(now)
				.updateBy(userRecord)
				.updateCtr(0)
				.build();
		
		var created = catalogSvc.create(List.of(toCreate)).get(0);
		
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

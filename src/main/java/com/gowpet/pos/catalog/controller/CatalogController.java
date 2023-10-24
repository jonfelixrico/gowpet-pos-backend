package com.gowpet.pos.catalog.controller;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.catalog.CatalogItem;
import com.gowpet.pos.catalog.ItemType;
import com.gowpet.pos.catalog.service.CatalogItemService;
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
	List<String> createGoods(@RequestBody List<CreateProductDto> newItems, @AuthenticationPrincipal UserDetails user) {
		var created = catalogSvc.create(newItems
				.stream()
				.map(item -> CatalogItem.builder()
						.name(item.getName())
						.price(item.getPrice())
						.type(ItemType.PRODUCT)
						.createDt(Instant.now())
						.createBy(userSvc.findByUsername(user.getUsername()))
						.build())
				.collect(Collectors.toList()));
		
		return created.stream().map(item -> item.getId()).collect(Collectors.toList());
	}
	
	@GetMapping("/product/{id}")
	CatalogItem getProduct(@PathVariable String id) {
		return catalogSvc.get(id);
	}
	
	@DeleteMapping("/product/{id}")
	void deleteProduct(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
		catalogSvc.delete(id, userSvc.findByUsername(user.getUsername()));
	}
}

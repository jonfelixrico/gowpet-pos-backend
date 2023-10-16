package com.gowpet.pos.catalog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.catalog.CatalogItem;
import com.gowpet.pos.catalog.CatalogItemService;
import com.gowpet.pos.catalog.ItemType;

@RestController
@RequestMapping("/catalog")
class CatalogController {
	private CatalogItemService svc;
	
	CatalogController(CatalogItemService svc) {
		this.svc = svc;
	}

	@GetMapping
	List<CatalogItem> listItems() {
		return svc.listAll();
	}
	
	@PostMapping("/product")
	List<String> createGoods(@RequestBody List<CreateProductDto> newItems) {
		var created = svc.create(newItems
				.stream()
				.map(item -> CatalogItem.builder()
						.name(item.getName())
						.price(item.getPrice())
						.type(ItemType.PRODUCT)
						.build())
				.collect(Collectors.toList()));
		
		return created.stream().map(item -> item.getId()).collect(Collectors.toList());
	}
}

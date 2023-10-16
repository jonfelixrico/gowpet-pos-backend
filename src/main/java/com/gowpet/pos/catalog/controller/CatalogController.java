package com.gowpet.pos.catalog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.catalog.CatalogItem;
import com.gowpet.pos.catalog.CatalogItemService;

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
}

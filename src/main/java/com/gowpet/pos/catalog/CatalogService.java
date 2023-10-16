package com.gowpet.pos.catalog;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CatalogService {
	private CatalogItemRepository repo;
	
	public CatalogService(CatalogItemRepository repo) {
		this.repo = repo;
	}

	public CatalogItem createCatalogItem (CatalogItem catalog) {
		return repo.save(catalog);
	}
	
	public List<CatalogItem> getCatalogItems() {
		return repo.findAll();
	}
}

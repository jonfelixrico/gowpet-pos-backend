package com.gowpet.pos.catalog;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CatalogItemService {
	private CatalogItemRepository repo;
	
	public CatalogItemService(CatalogItemRepository repo) {
		this.repo = repo;
	}

	public CatalogItem createCatalogItem (CatalogItem catalog) {
		return repo.save(catalog);
	}
	
	public CatalogItem update(String id, CatalogItem item) {
		var record = repo.findById(id);
		if (record.isEmpty()) {
			return null;
		}
		
		return repo.save(item);
	}
	
	public List<CatalogItem> getCatalogItems() {
		return repo.findAll();
	}
}

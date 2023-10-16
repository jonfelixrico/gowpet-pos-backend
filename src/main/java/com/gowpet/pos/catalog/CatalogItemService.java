package com.gowpet.pos.catalog;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CatalogItemService {
	private CatalogItemRepository repo;
	
	public CatalogItemService(CatalogItemRepository repo) {
		this.repo = repo;
	}

	public CatalogItem create(CatalogItem catalog) {
		return repo.save(catalog);
	}
	
	public CatalogItem update(String id, CatalogItem item) {
		if (repo.existsById(id)) {
			return null;
		}
		
		return repo.save(item.withId(UUID.fromString(id)));
	}
	
	public List<CatalogItem> getCatalogItems() {
		return repo.findAll();
	}
}

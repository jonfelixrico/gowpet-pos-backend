package com.gowpet.pos.catalog;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CatalogItemService {
	private CatalogItemRepository crudRepo;
	
	public CatalogItemService(CatalogItemRepository repo) {
		this.crudRepo = repo;
	}

	public CatalogItem create(CatalogItem catalog) {
		return crudRepo.save(catalog);
	}
	
	public CatalogItem update(String id, CatalogItem item) {
		if (crudRepo.existsById(id)) {
			return null;
		}
		
		return crudRepo.save(item.withId(UUID.fromString(id)));
	}
	
	public List<CatalogItem> listAll() {
		return crudRepo.findAll();
	}
}

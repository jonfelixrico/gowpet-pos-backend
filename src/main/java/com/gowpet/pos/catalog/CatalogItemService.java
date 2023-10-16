package com.gowpet.pos.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;


@Service
public class CatalogItemService {
	private CatalogItemRepository repo;
	
	public CatalogItemService(CatalogItemRepository repo) {
		this.repo = repo;
	}

	public List<CatalogItem> create(List<CatalogItem> items) {
		var saved = repo.saveAll(items);
		var results = new ArrayList<CatalogItem>();
		saved.forEach(results::add);
		return results;
	}
	
	public CatalogItem update(String id, CatalogItem item) {
		if (repo.existsById(id)) {
			return null;
		}
		
		return repo.save(item.withId(id));
	}

	// TODO make this paginated
	public List<CatalogItem> listAll() {
		return repo.findAll();
	}
}

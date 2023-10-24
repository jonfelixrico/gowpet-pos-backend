package com.gowpet.pos.catalog;

import java.util.ArrayList;
import java.util.List;

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

	// TODO make this paginated
	public List<CatalogItem> listAll() {
		return repo.findAll();
	}
}

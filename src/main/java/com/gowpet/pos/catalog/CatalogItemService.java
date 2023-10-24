package com.gowpet.pos.catalog;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gowpet.pos.catalog.db.CatalogItemRepository;
import com.gowpet.pos.user.service.User;


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
	
	public void delete(String id, User user) {
		var record = repo.findById(id);
		if (record.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		var modifiedRecord = record.get().withDeleteBy(user).withDeleteDt(Instant.now());
		repo.save(modifiedRecord);
	}

	// TODO make this paginated
	public List<CatalogItem> listAll() {
		return repo.findAll();
	}
}

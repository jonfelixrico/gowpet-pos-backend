package com.gowpet.pos.catalog;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

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
	
	public void delete(String id, User deleteBy) {
		var record = get(id);
		var modifiedRecord = record.withDeleteBy(deleteBy).withDeleteDt(Instant.now());
		repo.save(modifiedRecord);
	}
	
	public CatalogItem get(String id) {
		var record = repo.findById(id);
		if (record.isEmpty() ||
				// check if not deleted
				record.get().getDeleteBy() != null) {
			// TODO consider using a custom error
			throw new NoSuchElementException();
		}
		
		return record.get();
	}

	// TODO make this paginated
	public List<CatalogItem> listAll() {
		var results = repo.findAll(CatalogItemSpecifications.isNotDeleted());
		return StreamSupport.stream(results.spliterator(), false)
				.collect(Collectors.toList());
	}
}

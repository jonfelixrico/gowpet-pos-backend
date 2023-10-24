package com.gowpet.pos.catalog.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.gowpet.pos.catalog.CatalogItem;
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
	
	private CatalogItem findByIdHelper(String id) {
		var record = repo.findById(id);
		if (record.isEmpty() ||
				// check if not deleted
				record.get().getDeleteBy() != null) {
			throw new NoSuchElementException();
		}
		
		return record.get();
	}
	
	public void delete(String id, User deleteBy) {
		var record = findByIdHelper(id);
		var modifiedRecord = record.withDeleteBy(deleteBy).withDeleteDt(Instant.now());
		repo.save(modifiedRecord);
	}

	// TODO make this paginated
	public List<CatalogItem> listAll() {
		return StreamSupport.stream(repo.findAll(CatalogItemSpecifications.isNotDeleted()).spliterator(), false)
				.collect(Collectors.toList());
	}
}

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
		var modifiedRecord = record
				.withStatus(ItemStatus.DELETED)
				.withUpdateDt(Instant.now())
				.withUpdateBy(deleteBy)
				.withUpdateCtr(record.getUpdateCtr() + 1);
		repo.save(modifiedRecord);
	}
	
	public CatalogItem get(String id) {
		var result = repo.findById(id);
		if (result.isEmpty()) {
			// TODO consider using a custom error
			throw new NoSuchElementException();
		}
		
		var record = result.get();
		if (record.getStatus() != null && record.getStatus().equals(ItemStatus.DELETED)) {
			throw new NoSuchElementException();
		}
		
		return result.get();
	}

	// TODO make this paginated
	public List<CatalogItem> listAll() {
		var results = repo.findAll(CatalogItemSpecifications.isNotDeleted());
		return StreamSupport.stream(results.spliterator(), false)
				.collect(Collectors.toList());
	}
}

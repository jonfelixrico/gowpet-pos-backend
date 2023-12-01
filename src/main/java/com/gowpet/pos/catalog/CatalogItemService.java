package com.gowpet.pos.catalog;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gowpet.pos.user.service.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
public class CatalogItemService {
	private CatalogItemRepository repo;
	
	CatalogItemService(CatalogItemRepository repo) {
		this.repo = repo;
	}
	
	public CatalogItem create(InsertFields item, User user) {
		var now = Instant.now();
		var preparedForSaving = CatalogItem.builder()
				.name(item.getName())
				.price(item.getPrice())
				.type(ItemType.PRODUCT)
				.createDt(now)
				.createBy(user)
				.updateDt(now)
				.updateBy(user)
				.updateCtr(0)
				.build();
		
		return repo.save(preparedForSaving);
	}
	
	public void delete(String id, User deleteBy) {
		var record = get(id);
		var modifiedRecord = record.toBuilder()
				.status(ItemStatus.DELETED)
				.updateDt(Instant.now())
				.updateBy(deleteBy)
				.updateCtr(record.getUpdateCtr() + 1)
				.build();
		repo.save(modifiedRecord);
	}
	
	public CatalogItem update(String id, UpdateableFields toUpdate, User updateBy) {
		var record = get(id);
		var modifiedRecord = record.toBuilder()
				.updateDt(Instant.now())
				.updateBy(updateBy)
				.updateCtr(record.getUpdateCtr() + 1)
				.price(toUpdate.getPrice())
				.name(toUpdate.getName())
				.build();
		return repo.save(modifiedRecord);
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
	
	public Page<CatalogItem> list(int pageNo, int itemCount, List<ItemType> type, String pattern) {
		List<Specification<CatalogItem>> andConditions = new ArrayList<>();
		andConditions.add(CatalogItemSpecifications.isNotDeleted());
		
		if (pattern == null || pattern.isEmpty()) {
			andConditions.add(CatalogItemSpecifications.nameLike(pattern));
		}
		
		return repo.findAll(Specification.allOf(
					CatalogItemSpecifications.isNotDeleted(),
					CatalogItemSpecifications.nameLike(pattern),
					CatalogItemSpecifications.typeIncludes(type)
				), PageRequest.of(pageNo, itemCount));
	}

	// TODO make this paginated
	public List<CatalogItem> listAll() {
		var results = repo.findAll(CatalogItemSpecifications.isNotDeleted());
		return StreamSupport.stream(results.spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@Getter
	@AllArgsConstructor(access = AccessLevel.PACKAGE)
	public static class UpdateableFields {
		private String name;
		private Double price;
	}
	
	@Getter
	@AllArgsConstructor(access = AccessLevel.PACKAGE)
	public static class InsertFields {
		private String name;
		private Double price;
	}
}

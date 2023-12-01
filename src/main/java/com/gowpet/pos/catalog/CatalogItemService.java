package com.gowpet.pos.catalog;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

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
	private final CatalogItemRepository repo;
	
	CatalogItemService(CatalogItemRepository repo) {
		this.repo = repo;
	}


	private void addFieldsToBuilder(CatalogItemFields fields, CatalogItem.CatalogItemBuilder builder) {
		builder.name(fields.getName()).price(fields.getPrice());
	}
	
	public CatalogItem create(CatalogItemFields item, User user) {
		var now = Instant.now();
		var builder = CatalogItem.builder()
				.type(ItemType.PRODUCT)
				.createDt(now)
				.createBy(user)
				.updateDt(now)
				.updateBy(user)
				.updateCtr(0);
		addFieldsToBuilder(item, builder);

		return repo.save(builder.build());
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
	
	public CatalogItem update(String id, CatalogItemFields toUpdate, User updateBy) {
		var record = get(id);
		var builder = record.toBuilder()
				.updateDt(Instant.now())
				.updateBy(updateBy)
				.updateCtr(record.getUpdateCtr() + 1);
		addFieldsToBuilder(toUpdate, builder);
		
		return repo.save(builder.build());
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
		return repo.findAll(Specification.allOf(
					CatalogItemSpecifications.isNotDeleted(),
					CatalogItemSpecifications.nameLike(pattern),
					CatalogItemSpecifications.typeIncludes(type)
				), PageRequest.of(pageNo, itemCount));
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class CatalogItemFields {
		private String name;
		private Double price;
	}
}

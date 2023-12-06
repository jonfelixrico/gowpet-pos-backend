package com.gowpet.pos.catalog;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
		builder.name(fields.getName()).price(fields.getPrice()).code(fields.getCode()).codeType(fields.getCodeType());
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
		var record = findById(id).orElseThrow();

		var modifiedRecord = record.toBuilder()
				.status(ItemStatus.DELETED)
				.updateDt(Instant.now())
				.updateBy(deleteBy)
				.updateCtr(record.getUpdateCtr() + 1)
				.build();
		repo.save(modifiedRecord);
	}
	
	public CatalogItem update(String id, CatalogItemFields toUpdate, User updateBy) {
		var record = findById(id).orElseThrow();

		var builder = record.toBuilder()
				.updateDt(Instant.now())
				.updateBy(updateBy)
				.updateCtr(record.getUpdateCtr() + 1);
		addFieldsToBuilder(toUpdate, builder);

		return repo.save(builder.build());
	}
	
	public Optional<CatalogItem> findById(String id) {
		return repo.findOne(Specification.allOf(CatalogItemSpecifications.isNotDeleted(), CatalogItemSpecifications.id(id)));
	}

	public Optional<CatalogItem> findByCode(String code) {
		return repo.findOne(Specification.allOf(CatalogItemSpecifications.code(code), CatalogItemSpecifications.isNotDeleted()));
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

		private String code;
		private ItemCodeType codeType;
	}
}

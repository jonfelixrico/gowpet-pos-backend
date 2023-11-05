package com.gowpet.pos.catalog;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

class CatalogItemSpecifications {
	static Specification<CatalogItem> isNotDeleted() {
		Specification<CatalogItem> statusNotDeleted = (root, query, builder) -> {
			return builder.notEqual(root.get(CatalogItem_.status), ItemStatus.DELETED);
		};
		
		Specification<CatalogItem> statusIsNull = (root, query, builder) -> {
			return builder.isNull(root.get(CatalogItem_.status));
		};
		
		/*
		 * Just checking for statusNotDeleted doesn't seem to work. I have to null-check first via statusIsNull
		 * for it to work.
		 */
		return Specification.where(statusIsNull).or(statusNotDeleted);
	}
	
	static Specification<CatalogItem> nameLike(String like) {
		return (root, query, builder) -> {
			if (like == null || like.isEmpty()) {
				return builder.and(); // always-true
			}
			
			return builder.like(root.get(CatalogItem_.name), String.format("%s%%", like));
		};
	}
	
	static Specification<CatalogItem> typeIncludes(List<ItemType> types) {
		return (root, query, builder) -> {
			if (types == null || types.isEmpty()) {
				return builder.and(); // always-true
			}
			
			return root.get(CatalogItem_.type).in(types);
		};
	}
}

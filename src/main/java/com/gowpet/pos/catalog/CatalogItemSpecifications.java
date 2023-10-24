package com.gowpet.pos.catalog;

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
}

package com.gowpet.pos.catalog.db;

import org.springframework.data.jpa.domain.Specification;

import com.gowpet.pos.catalog.CatalogItem;

class CatalogItemSpecifications {
	static Specification<CatalogItem> isDeleted() {
		return (root, query, builder) -> {
			return builder.isNull(root.get(CatalogItem_.deleteDt));
		};
	}
}

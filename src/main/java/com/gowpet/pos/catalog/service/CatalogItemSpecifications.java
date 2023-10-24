package com.gowpet.pos.catalog.service;

import org.springframework.data.jpa.domain.Specification;

import com.gowpet.pos.catalog.CatalogItem;

class CatalogItemSpecifications {
	static Specification<CatalogItem> isNotDeleted() {
		return (root, query, builder) -> {
			return builder.isNull(root.get(CatalogItem_.deleteDt));
		};
	}
}

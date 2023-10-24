package com.gowpet.pos.catalog;

import org.springframework.data.jpa.domain.Specification;

class CatalogItemSpecifications {
	static Specification<CatalogItem> isNotDeleted() {
		return (root, query, builder) -> {
			return builder.notEqual(root.get(CatalogItem_.status), ItemStatus.DELETED);
		};
	}
}

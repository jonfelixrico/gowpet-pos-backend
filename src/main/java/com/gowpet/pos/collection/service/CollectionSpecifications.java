package com.gowpet.pos.collection.service;

import org.springframework.data.jpa.domain.Specification;

class CollectionSpecifications {
	static Specification<Collection> belongsToBilling(String billingId) {
		return (root, query, builder) -> builder.equal(root.get(Collection_.billing).get("id"), billingId);
	}
}

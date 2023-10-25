package com.gowpet.pos.billing.service;

import org.springframework.data.jpa.domain.Specification;

public class BillingSpecifications {
	static Specification<Billing> isNotDeleted() {
		Specification<Billing> statusNotDeleted = (root, query, builder) -> {
			return builder.notEqual(root.get(Billing_.recordStatus), RecordStatus.DELETED);
		};
		
		Specification<Billing> statusIsNull = (root, query, builder) -> {
			return builder.isNull(root.get(Billing_.recordStatus));
		};
		
		/*
		 * Just checking for statusNotDeleted doesn't seem to work. I have to null-check first via statusIsNull
		 * for it to work.
		 */
		return Specification.where(statusIsNull).or(statusNotDeleted);
	}
}

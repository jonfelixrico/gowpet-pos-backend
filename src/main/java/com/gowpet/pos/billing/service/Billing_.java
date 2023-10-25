package com.gowpet.pos.billing.service;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Billing.class)
class Billing_ {
	static volatile SingularAttribute<Billing, RecordStatus> recordStatus;
}

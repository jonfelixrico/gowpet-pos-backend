package com.gowpet.pos.collection.service;

import com.gowpet.pos.billing.service.Billing;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value = Collection.class)
class Collection_ {
	static volatile SingularAttribute<Collection, Billing> billing;
}

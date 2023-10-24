package com.gowpet.pos.catalog;

import java.time.Instant;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CatalogItem.class)
class CatalogItem_ {
	public static volatile SingularAttribute<CatalogItem, Instant> deleteDt;
}

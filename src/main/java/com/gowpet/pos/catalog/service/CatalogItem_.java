package com.gowpet.pos.catalog.service;

import java.time.Instant;

import com.gowpet.pos.catalog.CatalogItem;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CatalogItem.class)
class CatalogItem_ {
	static volatile SingularAttribute<CatalogItem, Instant> deleteDt;
	static volatile SingularAttribute<CatalogItem, String> id;
}

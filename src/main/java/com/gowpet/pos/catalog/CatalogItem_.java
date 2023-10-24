package com.gowpet.pos.catalog;

import java.time.Instant;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Metamodels need to be in the same package as their main model
 * @author Felix
 *
 */
@StaticMetamodel(CatalogItem.class)
class CatalogItem_ {
	static volatile SingularAttribute<CatalogItem, Instant> deleteDt;
}

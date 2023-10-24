package com.gowpet.pos.catalog.db;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.gowpet.pos.catalog.CatalogItem;


interface CatalogItemRepository extends CrudRepository<CatalogItem, String>, JpaSpecificationExecutor<CatalogItem> {
}

package com.gowpet.pos.catalog;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;


interface CatalogItemRepository extends CrudRepository<CatalogItem, String>, JpaSpecificationExecutor<CatalogItem> {
}

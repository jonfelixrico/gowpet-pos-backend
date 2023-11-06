package com.gowpet.pos.catalog;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


interface CatalogItemRepository extends CrudRepository<CatalogItem, String>, JpaSpecificationExecutor<CatalogItem>, PagingAndSortingRepository<CatalogItem, String> {
}

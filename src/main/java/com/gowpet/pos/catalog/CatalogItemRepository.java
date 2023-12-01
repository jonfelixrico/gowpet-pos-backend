package com.gowpet.pos.catalog;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


interface CatalogItemRepository extends CrudRepository<CatalogItem, String>, JpaSpecificationExecutor<CatalogItem>, PagingAndSortingRepository<CatalogItem, String> {
    Optional<CatalogItem> findByCode(String code);
}

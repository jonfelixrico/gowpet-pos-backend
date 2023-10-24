package com.gowpet.pos.catalog.db;

import org.springframework.data.repository.CrudRepository;

import com.gowpet.pos.catalog.CatalogItem;


public interface CatalogItemRepository extends CrudRepository<CatalogItem, String> {
}

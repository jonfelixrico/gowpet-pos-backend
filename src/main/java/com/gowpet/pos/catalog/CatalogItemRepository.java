package com.gowpet.pos.catalog;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;


interface CatalogItemRepository extends CrudRepository<CatalogItem, UUID> {
	List<CatalogItem> findAll();
}

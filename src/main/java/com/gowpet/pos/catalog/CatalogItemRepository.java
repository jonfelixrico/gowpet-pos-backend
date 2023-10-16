package com.gowpet.pos.catalog;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


interface CatalogItemRepository extends CrudRepository<CatalogItem, String> {
	List<CatalogItem> findAll();
}

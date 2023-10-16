package com.gowpet.pos.catalog;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;


interface CatalogItemRepository extends CrudRepository<CatalogItem, UUID> {
}

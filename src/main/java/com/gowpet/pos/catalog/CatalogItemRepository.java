package com.gowpet.pos.catalog;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;


public interface CatalogItemRepository extends CrudRepository<CatalogItem, UUID> {
}

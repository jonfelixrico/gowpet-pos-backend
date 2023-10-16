package com.gowpet.pos.catalog;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

interface CatalogItemPagingRepository extends PagingAndSortingRepository<CatalogItem, UUID> {
	Page<CatalogItem> findByNameContaining(String name, Pageable pageable);
	Page<CatalogItem> findAll();
}

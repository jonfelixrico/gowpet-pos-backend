package com.gowpet.pos.catalog;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CatalogItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	private String name;

	public CatalogItem(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	
	CatalogItem() {}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

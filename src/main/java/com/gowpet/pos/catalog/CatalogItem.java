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
	private Float price;
	private ItemType type;
	
	public CatalogItem(UUID id, String name, Float price, ItemType type) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
	}

	CatalogItem() {}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Float getPrice() {
		return price;
	}

	public ItemType getType() {
		return type;
	}	
}

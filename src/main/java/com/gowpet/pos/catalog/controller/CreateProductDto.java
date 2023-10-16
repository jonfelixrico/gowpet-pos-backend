package com.gowpet.pos.catalog.controller;

import com.gowpet.pos.catalog.ItemType;

import lombok.Getter;

@Getter
class CreateProductDto {
	private String name;
	private Float price;
	private ItemType type;
}

package com.gowpet.pos.billing.controller;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
class BillingRespDto {
	private String id;
	private String notes;
	private List<BillingItemRespDto> items;
	private Long serialNo;
	
	@Getter
	@AllArgsConstructor(access = AccessLevel.PACKAGE)
	@Builder
	static class BillingItemRespDto {
		private CatalogItem catalogItem;
		private Double price;
		private Double quantity;
		private Double priceOverride;
		private String notes;
	}
	
	@Getter
	@AllArgsConstructor(access = AccessLevel.PACKAGE)
	@Builder
	static class CatalogItem {
		private String id;
		private String name;
	}
}

package com.gowpet.pos.billing.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
class BillingRespDto {
	private String id;
	private Double amountOverride;
	private String notes;
	
	@Getter
	@AllArgsConstructor(access = AccessLevel.PACKAGE)
	@Builder
	static class BillingItemRespDto {
		private CatalogItem catalogItem;
		private Double price;
		private Double quantity;
	}
	
	@Getter
	@AllArgsConstructor(access = AccessLevel.PACKAGE)
	@Builder
	static class CatalogItem {
		private String id;
		private String name;
	}
}

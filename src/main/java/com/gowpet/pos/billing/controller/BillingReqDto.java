package com.gowpet.pos.billing.controller;

import java.util.List;

import lombok.Getter;

@Getter
public class BillingReqDto{
	private List<BillingItemReqDto> items;
	private String notes;
	
	@Getter
	static class BillingItemReqDto {
		private String catalogId;
		private Double quantity;
		
		private Double priceOverride;
		private String notes;
	}
}

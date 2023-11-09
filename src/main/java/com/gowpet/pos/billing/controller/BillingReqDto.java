package com.gowpet.pos.billing.controller;

import java.util.List;

import com.gowpet.pos.billing.service.BillingService.BillingInput;
import com.gowpet.pos.billing.service.BillingService.BillingItemInput;

import lombok.Getter;

@Getter
public class BillingReqDto{
	private List<BillingItemReqDto> items;
	private String notes;
	
	@Getter
	static class BillingItemReqDto {
		private String catalogId;
		private Double quantity;
	}
}

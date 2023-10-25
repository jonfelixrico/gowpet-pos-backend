package com.gowpet.pos.billing.controller;

import java.util.List;

import com.gowpet.pos.billing.service.BillingService.BillingInput;
import com.gowpet.pos.billing.service.BillingService.BillingItemInput;

import lombok.Getter;

@Getter
public class BillingDto extends BillingInput {
	protected List<BillingItemDto> items;
	
	@Getter
	static class BillingItemDto extends BillingItemInput {}
}

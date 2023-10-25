package com.gowpet.pos.billing.controller;

import java.util.List;

import com.gowpet.pos.billing.service.BillingService.BillingInput;
import com.gowpet.pos.billing.service.BillingService.BillingItemInput;

import lombok.Getter;

@Getter
public class BillingReqDto extends BillingInput {
	protected List<BillingItemReqDto> items;
	
	@Getter
	static class BillingItemReqDto extends BillingItemInput {}
}

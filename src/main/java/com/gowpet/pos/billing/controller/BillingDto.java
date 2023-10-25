package com.gowpet.pos.billing.controller;

import java.util.List;

import com.gowpet.pos.billing.service.BillingService.BillingInput;
import com.gowpet.pos.billing.service.BillingService.BillingItemInput;

public class BillingDto extends BillingInput {
	protected List<BillingItemDto> items;
	static class BillingItemDto extends BillingItemInput {}
}

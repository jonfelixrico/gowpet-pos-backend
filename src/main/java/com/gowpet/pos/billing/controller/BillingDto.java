package com.gowpet.pos.billing.controller;

import java.util.List;

import com.gowpet.pos.billing.service.BillingService.BillingInput;
import com.gowpet.pos.billing.service.BillingService.BillingItemInput;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class BillingDto extends BillingInput {
	protected List<BillingItemDto> items;
	
	@NoArgsConstructor(access = AccessLevel.PACKAGE)
	static class BillingItemDto extends BillingItemInput {}
}

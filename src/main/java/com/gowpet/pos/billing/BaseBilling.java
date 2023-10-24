package com.gowpet.pos.billing;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public abstract class BaseBilling {
	protected List<? extends BillingItem> items;
	
	protected Double amountOverride;
	
	protected String notes;
}

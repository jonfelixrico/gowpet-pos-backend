package com.gowpet.pos.billing;

import com.gowpet.pos.catalog.CatalogItem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Getter
public class BillingItem {
	protected String id;
	
	protected CatalogItem item;
	protected Double price;
	
	protected Double quantity;
	
	protected Billing billing;
}

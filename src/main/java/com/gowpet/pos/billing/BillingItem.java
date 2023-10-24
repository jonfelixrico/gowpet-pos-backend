package com.gowpet.pos.billing;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
public class BillingItem extends BaseBillingItem {
	protected String id;
	protected Billing billing;
}

package com.gowpet.pos.billing;

import java.time.Instant;
import java.util.List;

import com.gowpet.pos.user.service.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Getter
public class Billing {
	protected String id;
	
	protected List<? extends BillingItem> items;
	
	protected Double amountOverride;
	
	protected String notes;
	
	protected Instant createDt;
	protected User createBy;
	
}

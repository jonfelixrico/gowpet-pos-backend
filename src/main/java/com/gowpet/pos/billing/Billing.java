package com.gowpet.pos.billing;

import java.time.Instant;

import com.gowpet.pos.user.service.User;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
public class Billing extends BaseBilling {
	protected String id;
	
	protected Instant createDt;
	protected User createBy;
	
}

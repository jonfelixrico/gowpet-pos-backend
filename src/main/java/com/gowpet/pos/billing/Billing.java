package com.gowpet.pos.billing;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
public class Billing extends BaseBilling {
	protected String id;
}

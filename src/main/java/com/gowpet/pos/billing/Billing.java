package com.gowpet.pos.billing;

import java.time.Instant;

import com.gowpet.pos.user.service.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
public class Billing extends BaseBilling {
	protected String id;
	
	protected Instant createDt;
	@ManyToOne
	@JoinColumn
	protected User createBy;
	
}

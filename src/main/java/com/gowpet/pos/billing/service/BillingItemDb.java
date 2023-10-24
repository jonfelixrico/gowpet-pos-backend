package com.gowpet.pos.billing.service;

import com.gowpet.pos.billing.BillingItem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Entity
@Getter
public class BillingItemDb extends BillingItem {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	protected String id;
	
	@ManyToOne
	@JoinColumn
	protected BillingDb billing;
}

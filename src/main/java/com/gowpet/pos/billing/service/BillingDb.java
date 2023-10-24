package com.gowpet.pos.billing.service;

import java.util.ArrayList;
import java.util.List;

import com.gowpet.pos.billing.Billing;
import com.gowpet.pos.billing.BillingItem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Entity
public class BillingDb extends Billing {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	protected String id;
	
	@Builder.Default
	@OneToMany(orphanRemoval = true)
	protected List<? extends BillingItem> items = new ArrayList<>();
}

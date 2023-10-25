package com.gowpet.pos.billing.service;

import java.util.ArrayList;
import java.util.List;

import com.gowpet.pos.user.service.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Entity
@Getter
public class BillingDb extends Billing {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	protected String id;
	
	@Builder.Default
	@OneToMany(orphanRemoval = true,
		fetch = FetchType.EAGER,
		targetEntity = BillingItemDb.class,
		cascade = CascadeType.ALL,
		mappedBy = "billing")
	protected List<? extends BillingItem> items = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn
	protected User createBy;
}

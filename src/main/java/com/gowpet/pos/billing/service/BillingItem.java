package com.gowpet.pos.billing.service;

import com.gowpet.pos.catalog.CatalogItem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(toBuilder = true)
@Getter
@Entity
public class BillingItem {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	protected String id;
	
	@ManyToOne
	@JoinColumn
	protected CatalogItem catalogItem;
	protected Double price;
	
	protected Double quantity;
	
	@ManyToOne
	@JoinColumn
	protected Billing billing;
}

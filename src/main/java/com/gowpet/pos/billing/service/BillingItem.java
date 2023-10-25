package com.gowpet.pos.billing.service;

import com.gowpet.pos.catalog.CatalogItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	protected CatalogItem catalogItem;
	
	@Column(nullable = false)
	protected Double price;
	
	@Column(nullable = false)
	protected Double quantity;
	
	@Column(nullable = false)
	protected Integer itemNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	protected Billing billing;
}

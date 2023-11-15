package com.gowpet.pos.billing.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.gowpet.pos.user.service.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Billing {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	protected String id;
	
	@Builder.Default
	@OneToMany(orphanRemoval = true,
		fetch = FetchType.LAZY,
		targetEntity = BillingItem.class,
		cascade = CascadeType.ALL)
	@JoinColumn
	protected List<BillingItem> items = new ArrayList<>();
	
	
	protected String notes;
	
	@Column(nullable = false)
	protected Instant createDt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	protected User createBy;

	@GeneratedValue
	private Long serialNo;
}

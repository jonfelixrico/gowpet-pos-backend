package com.gowpet.pos.collection.service;

import java.time.Instant;

import com.gowpet.pos.billing.service.Billing;
import com.gowpet.pos.user.service.User;

import jakarta.persistence.Column;
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

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Collection {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable = false)
	private Double amountPaid;
	private String notes;
	
	@Column(nullable = false)
	private Instant createDt;
	@ManyToOne
	@JoinColumn(nullable = false)
	private User createBy;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Billing billing;
}

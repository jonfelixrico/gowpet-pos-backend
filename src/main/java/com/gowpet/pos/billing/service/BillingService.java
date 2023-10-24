package com.gowpet.pos.billing.service;

import org.springframework.stereotype.Service;

@Service
public class BillingService {
	private BillingRepository repo;

	BillingService(BillingRepository repo) {
		this.repo = repo;
	}
}

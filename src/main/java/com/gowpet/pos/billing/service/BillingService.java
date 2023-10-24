package com.gowpet.pos.billing.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gowpet.pos.billing.BaseBilling;
import com.gowpet.pos.billing.Billing;

import lombok.experimental.SuperBuilder;

@Service
public class BillingService {
	private BillingRepository repo;

	BillingService(BillingRepository repo) {
		this.repo = repo;
	}
	
	public Billing get(String id) {
		var result = repo.findById(id);
		if (result.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return result.get();
	}
	
	@SuperBuilder(toBuilder = true)
	public static class NewBilling extends BaseBilling {
	}
}

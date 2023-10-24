package com.gowpet.pos.billing.service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gowpet.pos.billing.Billing;
import com.gowpet.pos.user.service.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
	
	@Getter
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	@SuperBuilder(toBuilder = true)
	public static class NewBilling {
		private List<String> items;
		private Double amountOverride;
		private String notes;
	}
	
	public Billing create(NewBilling newBilling, User author) {
		var toSaveToDb = BillingDb.builder()
				// TODO Impl item mapping
//				.items(newBilling.getItems())
				.amountOverride(newBilling.getAmountOverride())
				.notes(newBilling.getNotes())
				.createDt(Instant.now())
				.createBy(author)
				.build();
		
		return repo.save(toSaveToDb);
	}
}

package com.gowpet.pos.billing.service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gowpet.pos.billing.Billing;
import com.gowpet.pos.user.service.User;

import lombok.Getter;

@Service
public class BillingService {
	private BillingRepository billingRepo;
	private BillingItemRepository itemRepo;
	
	BillingService(BillingRepository billingRepo, BillingItemRepository itemRepo) {
		this.billingRepo = billingRepo;
		this.itemRepo = itemRepo;
	}

	public Billing get(String id) {
		var result = billingRepo.findById(id);
		if (result.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return result.get();
	}
	
	@Getter
	public static abstract class NewBilling {
		private List<NewBillingItem> items;
		private Double amountOverride;
		private String notes;
	}
	
	public static abstract class NewBillingItem {
		private String catalogId;
		private String quantity;
		private Double price;
	}
	
	private BillingItemDb getItemHelper(String id) {
		var result = itemRepo.findById(id);
		if (result.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return result.get();
	}
	
	public Billing create(NewBilling newBilling, User author) {
		var mappedItems = newBilling.getItems()
				.stream()
				.map(id -> getItemHelper(id))
				.toList();

		var toSaveToDb = BillingDb.builder()
				.items(mappedItems)
				.amountOverride(newBilling.getAmountOverride())
				.notes(newBilling.getNotes())
				.createDt(Instant.now())
				.createBy(author)
				.build();
		
		return billingRepo.save(toSaveToDb);
	}
}

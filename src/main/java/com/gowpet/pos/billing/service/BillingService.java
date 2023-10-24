package com.gowpet.pos.billing.service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gowpet.pos.billing.Billing;
import com.gowpet.pos.catalog.CatalogItemService;
import com.gowpet.pos.user.service.User;

import lombok.Getter;

@Service
public class BillingService {
	private BillingRepository billingRepo;
	private CatalogItemService catalogSvc;

	BillingService(BillingRepository billingRepo, CatalogItemService catalogSvc) {
		this.billingRepo = billingRepo;
		this.catalogSvc = catalogSvc;
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
	
	@Getter
	public static abstract class NewBillingItem {
		private String catalogId;
		private Double quantity;
		private Double price;
	}
	
	private BillingItemDb billingItemHelper (NewBillingItem item) {
		return BillingItemDb.builder()
				.item(catalogSvc.get(item.getCatalogId()))
				.price(item.getPrice())
				.quantity(item.getQuantity())
				.build();
	}
	
	public Billing create(NewBilling newBilling, User author) {
		var mappedItems = newBilling.getItems()
				.stream()
				.map(this::billingItemHelper)
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

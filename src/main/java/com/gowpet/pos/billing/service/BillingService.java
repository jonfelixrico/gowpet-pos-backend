package com.gowpet.pos.billing.service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

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
		
		var record = result.get();
		if (record.getRecordStatus() != null && record.getRecordStatus().equals(RecordStatus.DELETED)) {
			throw new NoSuchElementException();
		}
		
		return result.get();
	}
	
	@Getter
	public static abstract class NewBilling {
		protected List<? extends NewBillingItem> items;
		protected Double amountOverride;
		protected String notes;
	}
	
	@Getter
	public static abstract class NewBillingItem {
		protected String catalogId;
		protected Double quantity;
		protected Double price;
	}
	
	private BillingItem billingItemHelper (NewBillingItem item) {
		return BillingItem.builder()
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

		var now = Instant.now();
		var toSaveToDb = Billing.builder()
				.items(mappedItems)
				.amountOverride(newBilling.getAmountOverride())
				.notes(newBilling.getNotes())
				.createDt(now)
				.createBy(author)
				.updateCtr(0)
				.updateBy(author)
				.updateDt(now)
				.build();
		
		return billingRepo.save(toSaveToDb);
	}
	
	public void delete(String id, User deleteBy) {
		var record = get(id);
		record.toBuilder()
			.updateCtr(record.getUpdateCtr() + 1)
			.updateDt(Instant.now())
			.updateBy(deleteBy)
			.recordStatus(RecordStatus.DELETED);
	}
}

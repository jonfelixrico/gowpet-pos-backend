package com.gowpet.pos.billing.service;

import java.time.Instant;
import java.util.ArrayList;
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
	
	private BillingItem billingItemHelper (BillingItemInput item, int itemNo) {
		return BillingItem.builder()
				.catalogItem(catalogSvc.get(item.getCatalogId()))
				.price(item.getPrice())
				.quantity(item.getQuantity())
				.itemNo(itemNo)
				.build();
	}
	
	public Billing create(BillingInput newBilling, User author) {
		var mappedItems = new ArrayList<BillingItem>();
		var inputItems = newBilling.getItems();
		for (int i = 0; i < inputItems.size(); i++) {
			mappedItems.add(billingItemHelper(inputItems.get(i), i));
		}

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
		var builder = record.toBuilder()
			.updateCtr(record.getUpdateCtr() + 1)
			.updateDt(Instant.now())
			.updateBy(deleteBy)
			.recordStatus(RecordStatus.DELETED);
		
		billingRepo.save(builder.build());
	}
	
	@Getter
	public static abstract class BillingInput {
		protected List<? extends BillingItemInput> items;
		protected Double amountOverride;
		protected String notes;
	}
	
	@Getter
	public static abstract class BillingItemInput {
		protected String catalogId;
		protected Double quantity;
		protected Double price;
	}
}

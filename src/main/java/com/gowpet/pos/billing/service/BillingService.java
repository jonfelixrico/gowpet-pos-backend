package com.gowpet.pos.billing.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gowpet.pos.catalog.CatalogItemService;
import com.gowpet.pos.user.service.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Service
public class BillingService {
	private final BillingRepository billingRepo;
	private final CatalogItemService catalogSvc;

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

	public Page<Billing> list(int pageNo, int itemCount) {
		return billingRepo.findAll(PageRequest.of(pageNo, itemCount));
	}
	
	public Billing create(BillingInput newBilling, User author) {
		var now = Instant.now();
		var toSaveToDb = Billing.builder()
				.items(extractItems(newBilling))
				.notes(newBilling.getNotes())
				.createDt(now)
				.createBy(author)
				.build();
		
		return billingRepo.save(toSaveToDb);
	}
	
	private List<BillingItem> extractItems(BillingInput input) {
		var mappedItems = new ArrayList<BillingItem>();

		var inputItems = input.getItems();
		for (int i = 0; i < inputItems.size(); i++) {
			mappedItems.add(billingItemHelper(inputItems.get(i), i));
		}
		
		return mappedItems;
	}
	
	private BillingItem billingItemHelper (BillingItemInput item, int itemNo) {
		return BillingItem.builder()
				.catalogItem(catalogSvc.get(item.getCatalogId()))
				.price(item.getPrice())
				.quantity(item.getQuantity())
				.itemNo(itemNo)
				.priceOverride(item.getPriceOverride())
				.notes(item.getNotes())
				.build();
	}
	
	@Getter
	@Builder
	@AllArgsConstructor(access = AccessLevel.PACKAGE)
	public static class BillingInput {
		private List<? extends BillingItemInput> items;
		private String notes;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor(access = AccessLevel.PACKAGE)
	public static class BillingItemInput {
		private String catalogId;
		private Double quantity;
		private Double price;
		private Double priceOverride;
		private String notes;
	}
}

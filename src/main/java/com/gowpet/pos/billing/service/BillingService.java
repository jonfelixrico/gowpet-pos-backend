package com.gowpet.pos.billing.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

	/**
	 * This generates the next serial no based on the max serial no in the billing table.
	 * Please see {@link Billing#serialNo} for more info about why we're doing this instead of letting
	 * JPA take care of it.
	 */
	private Long getNextId() {
		var withMaxId = billingRepo.findTopByOrderBySerialNoDesc();
		var maxId = withMaxId.isEmpty() ? 0 : withMaxId.get().getSerialNo();
		return maxId + 1;
	}

	public Billing create(BillingInput newBilling, User author) {
		var now = Instant.now();

		var toSaveToDb = Billing.builder()
				.items(extractItems(newBilling))
				.notes(newBilling.getNotes())
				.createDt(now)
				.createBy(author)
				.serialNo(getNextId())
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
				.catalogItem(catalogSvc.findById(item.getCatalogId()).orElseThrow())
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

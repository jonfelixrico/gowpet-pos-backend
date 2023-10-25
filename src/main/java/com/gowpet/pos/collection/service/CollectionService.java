package com.gowpet.pos.collection.service;

import java.time.Instant;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gowpet.pos.billing.service.Billing;
import com.gowpet.pos.user.service.User;

import lombok.Getter;

@Service
public class CollectionService {
	private CollectionRepository repo;

	CollectionService(CollectionRepository repo) {
		this.repo = repo;
	}
	
	public Collection create(Billing billing,
			CollectionInput newCollection,
			User createBy) {
		var toSave = Collection.builder()
				.amountPaid(newCollection.amountPaid)
				.notes(newCollection.notes)
				.createDt(Instant.now())
				.createBy(createBy)
				.billing(billing)
				.build();
		
		return repo.save(toSave);
	}
	
	public Collection get(String id) {
		var result = repo.findById(id);
		if (result.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return result.get();
	}
	
	@Getter
	public static class CollectionInput {
		private Double amountPaid;
		private String notes;
	}
}

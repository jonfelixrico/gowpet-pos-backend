package com.gowpet.pos.collection.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.billing.service.BillingService;
import com.gowpet.pos.collection.service.Collection;
import com.gowpet.pos.collection.service.CollectionService;
import com.gowpet.pos.user.service.UserService;

@RestController
@RequestMapping("/billing/{billingId}/collection")
public class BillingCollectionController {
	private CollectionService collSvc;
	private BillingService billSvc;
	private UserService userSvc;
	
	BillingCollectionController(CollectionService collSvc, BillingService billSvc, UserService userSvc) {
		this.collSvc = collSvc;
		this.billSvc = billSvc;
		this.userSvc = userSvc;
	}

	@PostMapping
	private Collection submitPayment(@PathVariable String billingId,
			@AuthenticationPrincipal UserDetails user,
			@RequestBody CollectionReqDto newCollection) {
		var billing = billSvc.get(billingId);
		return collSvc.create(billing, newCollection, userSvc.findByUsername(user.getUsername()));
	}
	
	@GetMapping("/{collectionId}")
	private Collection getBillingCollection(@PathVariable String billingId,
			@PathVariable String collectionId) {
		var collection = collSvc.get(collectionId);
		
		if (!collection.getBilling().getId().equals(billingId)) {
			throw new NoSuchElementException();
		}
		
		return collection;
	}
	
	@GetMapping
	private List<Collection> listBillingCollections() {
		return collSvc.list();
	}
}

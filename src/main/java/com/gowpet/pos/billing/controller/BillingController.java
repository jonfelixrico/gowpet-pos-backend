package com.gowpet.pos.billing.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.billing.service.Billing;
import com.gowpet.pos.billing.service.BillingService;
import com.gowpet.pos.user.service.UserService;

@RestController
@RequestMapping("/billing")
public class BillingController {
	private BillingService billingSvc;
	private UserService userSvc;
	
	BillingController(BillingService billingSvc, UserService userSvc) {
		this.billingSvc = billingSvc;
		this.userSvc = userSvc;
	}

	@GetMapping("/{id}")
	Billing getBilling(@PathVariable String id) {
		return billingSvc.get(id);
	}
	
	@PostMapping
	Billing createBilling(@RequestBody BillingDto newBilling,
			@AuthenticationPrincipal UserDetails user) {
		return billingSvc.create(newBilling, userSvc.findByUsername(user.getUsername()));
	}
	
	@DeleteMapping("/{id}")
	void deleteBilling(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
		billingSvc.delete(id, userSvc.findByUsername(user.getUsername()));
	}
	
	@PutMapping("/{id}")
	Billing updateBilling(@PathVariable String id, 
			@AuthenticationPrincipal UserDetails user, 
			@RequestBody BillingDto updated) {
		return billingSvc.update(id, updated, userSvc.findByUsername(user.getUsername()));
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	ResponseEntity<ErrorResponse> handleNoSuchElement() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}

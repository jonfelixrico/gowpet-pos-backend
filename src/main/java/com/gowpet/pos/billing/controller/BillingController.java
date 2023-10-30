package com.gowpet.pos.billing.controller;

import java.util.List;
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
import com.gowpet.pos.billing.service.BillingItem;
import com.gowpet.pos.billing.service.BillingService;
import com.gowpet.pos.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/billing")
@SecurityRequirement(name = "bearerAuth")
public class BillingController {
	private BillingService billingSvc;
	private UserService userSvc;
	
	BillingController(BillingService billingSvc, UserService userSvc) {
		this.billingSvc = billingSvc;
		this.userSvc = userSvc;
	}
	
	private BillingRespDto.BillingItemRespDto convertBillingItemToDto(BillingItem item) {
		var catalogItem = BillingRespDto.CatalogItem.builder()
				.name(item.getCatalogItem().getName())
				.id(item.getCatalogItem().getId())
				.build();

		return BillingRespDto.BillingItemRespDto.builder()
				.price(item.getPrice())
				.quantity(item.getQuantity())
				.catalogItem(catalogItem)
				.build();
	}
	
	private BillingRespDto convertBillingToDto(Billing billing) {
		var items = billing.getItems().stream()
				.map(this::convertBillingItemToDto)
				.toList();
		
		return BillingRespDto.builder()
				.id(billing.getId())
				.amountOverride(billing.getAmountOverride())
				.notes(billing.getNotes())
				.items(items)
				.build();
				
	}

	@GetMapping("/{id}")
	BillingRespDto getBilling(@PathVariable String id) {
		return convertBillingToDto(billingSvc.get(id));
	}
	
	@GetMapping
	List<BillingRespDto> listBilling() {
		return billingSvc.list().stream()
				.map(this::convertBillingToDto)
				.toList();
	}
	
	@PostMapping
	BillingRespDto createBilling(@RequestBody BillingReqDto newBilling,
			@AuthenticationPrincipal UserDetails user) {
		var created =  billingSvc.create(newBilling, userSvc.findByUsername(user.getUsername()));
		return convertBillingToDto(created);
	}
	
	@DeleteMapping("/{id}")
	void deleteBilling(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
		billingSvc.delete(id, userSvc.findByUsername(user.getUsername()));
	}
	
	@PutMapping("/{id}")
	BillingRespDto updateBilling(@PathVariable String id, 
			@AuthenticationPrincipal UserDetails user, 
			@RequestBody BillingReqDto toUpdate) {
		var updated = billingSvc.update(id, toUpdate, userSvc.findByUsername(user.getUsername()));
		return convertBillingToDto(updated);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	ResponseEntity<ErrorResponse> handleNoSuchElement() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}

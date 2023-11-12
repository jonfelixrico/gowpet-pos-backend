package com.gowpet.pos.billing.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowpet.pos.billing.service.Billing;
import com.gowpet.pos.billing.service.BillingItem;
import com.gowpet.pos.billing.service.BillingService;
import com.gowpet.pos.billing.service.BillingService.BillingInput;
import com.gowpet.pos.billing.service.BillingService.BillingItemInput;
import com.gowpet.pos.catalog.CatalogItemService;
import com.gowpet.pos.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/billing")
@SecurityRequirement(name = "bearerAuth")
public class BillingController {
	private final BillingService billingSvc;
	private final UserService userSvc;
	private final CatalogItemService itemSvc;
	
	BillingController(BillingService billingSvc, UserService userSvc, CatalogItemService itemSvc) {
		this.billingSvc = billingSvc;
		this.userSvc = userSvc;
		this.itemSvc = itemSvc;
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
				.priceOverride(item.getPriceOverride())
				.notes(item.getNotes())
				.build();
	}
	
	private BillingRespDto convertBillingToDto(Billing billing) {
		var items = billing.getItems().stream()
				.map(this::convertBillingItemToDto)
				.toList();
		
		return BillingRespDto.builder()
				.id(billing.getId())
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
	
	private BillingInput dtoToInput(BillingReqDto dto) {
		var inputItems = dto.getItems()
				.stream()
				.map(item -> BillingItemInput.builder()
						.catalogId(item.getCatalogId())
						.quantity(item.getQuantity())
						.price(itemSvc.get(item.getCatalogId()).getPrice())
						.priceOverride(item.getPriceOverride())
						.notes(item.getNotes())
						.build())
				.toList();
		
		return BillingInput.builder()
				.notes(dto.getNotes())
				.items(inputItems)
				.build();
	}
	
	@PostMapping
	BillingRespDto createBilling(@RequestBody BillingReqDto newBilling,
			@AuthenticationPrincipal UserDetails user) {
		var created =  billingSvc.create(dtoToInput(newBilling), userSvc.findByUsername(user.getUsername()));
		return convertBillingToDto(created);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	ResponseEntity<ErrorResponse> handleNoSuchElement() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}

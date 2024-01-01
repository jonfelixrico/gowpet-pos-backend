package com.gowpet.pos.billing.controller;

import com.gowpet.pos.auth.service.SessionService;
import com.gowpet.pos.billing.service.Billing;
import com.gowpet.pos.billing.service.BillingItem;
import com.gowpet.pos.billing.service.BillingService;
import com.gowpet.pos.billing.service.BillingService.BillingInput;
import com.gowpet.pos.billing.service.BillingService.BillingItemInput;
import com.gowpet.pos.catalog.CatalogItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/billing")
@SecurityRequirement(name = "bearerAuth")
public class BillingController {
    private final BillingService billingSvc;
    private final CatalogItemService itemSvc;

    private final SessionService sessionSvc;

    BillingController(BillingService billingSvc, CatalogItemService itemSvc, SessionService sessionSvc) {
        this.billingSvc = billingSvc;
        this.itemSvc = itemSvc;
        this.sessionSvc = sessionSvc;
    }

    private BillingRespDto.BillingItemRespDto convertItemToDto(BillingItem item) {
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

    private BillingRespDto convertToDto(Billing billing) {
        var items = billing.getItems().stream()
                .map(this::convertItemToDto)
                .toList();

        return BillingRespDto.builder()
                .id(billing.getId())
                .notes(billing.getNotes())
                .items(items)
                .serialNo(billing.getSerialNo())
                .build();

    }

    @GetMapping("/{id}")
    BillingRespDto getBilling(@PathVariable String id) {
        return convertToDto(billingSvc.get(id));
    }

    @GetMapping
    ResponseEntity<List<BillingRespDto>> listBilling(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "30") Integer itemCount) {
        var page = billingSvc.list(pageNo, itemCount);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page.getTotalPages()))
                .body(page.stream().map(this::convertToDto).toList());
    }

    private BillingInput convertFromDto(BillingReqDto dto) {
        var inputItems = dto.getItems()
                .stream()
                .map(item -> BillingItemInput.builder()
                        .catalogId(item.getCatalogId())
                        .quantity(item.getQuantity())
                        .price(itemSvc.findById(item.getCatalogId()).orElseThrow().getPrice())
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
    BillingRespDto createBilling(@RequestBody BillingReqDto newBilling) {
        var sessionUser = sessionSvc.getSessionUser().orElseThrow();
        var created = billingSvc.create(convertFromDto(newBilling), sessionUser);
        return convertToDto(created);
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<ErrorResponse> handleNoSuchElement() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

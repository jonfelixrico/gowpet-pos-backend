package com.gowpet.pos.catalogreport.controller;

import com.gowpet.pos.billing.service.AggregatedBillingItem;
import com.gowpet.pos.billing.service.BillingService;
import com.gowpet.pos.catalog.CatalogItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalog/report")
@SecurityRequirement(name = "bearerAuth")
class CatalogReportController {
    private final BillingService billingSvc;
    private final CatalogItemService catalogSvc;

    CatalogReportController(BillingService billingSvc, CatalogItemService catalogSvc) {
        this.billingSvc = billingSvc;
        this.catalogSvc = catalogSvc;
    }

    @GetMapping
    CatalogReportDto getReport(@RequestParam Optional<Instant> start, @RequestParam Optional<Instant> end) {
        List<AggregatedBillingItem> aggregated = billingSvc.aggregateItems(start.orElse(Instant.ofEpochMilli(0)), end.orElse(Instant.now()));


        var catalogItemData = aggregated.stream()
                .map(AggregatedBillingItem::catalogItemId)
                .distinct()
                .map(id -> catalogSvc.findById(id).orElseThrow())
                .map(catalogItem -> CatalogReportDto.PartialCatalogItem.builder()
                        .id(catalogItem.getId())
                        .name(catalogItem.getName())
                        .build())
                .toList();

        return CatalogReportDto
                .builder()
                .references(catalogItemData)
                .entries(aggregated)
                .build();
    }
}

package com.gowpet.pos.catalogreport.controller;

import com.gowpet.pos.billing.service.AggregatedBillingItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
record CatalogReportDto(List<PartialCatalogItem> references, List<AggregatedBillingItem> entries) {
    @Builder
    record PartialCatalogItem(String id, String name) {
    }
}

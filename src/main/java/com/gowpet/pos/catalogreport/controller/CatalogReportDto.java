package com.gowpet.pos.catalogreport.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
record CatalogReportDto(List<PartialCatalogItem> references, List<CatalogReportEntry> entries) {
    @Builder
    record PartialCatalogItem(String id, String name) {
    }

    @Builder
    record CatalogReportEntry(String id, Double amount, Long price) {}
}

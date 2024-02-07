package com.gowpet.pos.catalogreport.controller;

import com.gowpet.pos.billing.service.BillingService;
import com.gowpet.pos.catalog.CatalogItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package com.gowpet.pos.catalogreport.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog/report")
@SecurityRequirement(name = "bearerAuth")
class CatalogReportController {
}

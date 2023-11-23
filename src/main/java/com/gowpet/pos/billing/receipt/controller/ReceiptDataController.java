package com.gowpet.pos.billing.receipt.controller;

import com.gowpet.pos.billing.receipt.ReceiptDataService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing/receipt")
@SecurityRequirement(name = "bearerAuth")
public class ReceiptDataController {
    private final ReceiptDataService receiptSvc;

    ReceiptDataController(ReceiptDataService receiptSvc) {
        this.receiptSvc = receiptSvc;
    }

    @GetMapping
    ReceiptDataDto getReceiptData() {
        var result = receiptSvc.getReceiptData();
        if (result.isEmpty()) {
            return null;
        }

        var data = result.get();
        return ReceiptDataDto
                .builder()
                .header(data.getHeader())
                .address(data.getAddress())
                .contactNo(data.getContactNo())
                .snsLink(data.getSnsLink())
                .snsMessage(data.getSnsMessage())
                .build();
    }

    @PutMapping
    void setReceiptData(@RequestBody ReceiptDataDto data) {
        receiptSvc.setReceiptData(ReceiptDataService.ReceiptDataInput.builder()
                .header(data.getHeader())
                .address(data.getAddress())
                .contactNo(data.getContactNo())
                .snsLink(data.getSnsLink())
                .snsMessage(data.getSnsMessage())
                .build());
    }

    @DeleteMapping
    void clearReceiptData() {
        receiptSvc.clearReceiptData();
    }
}

package com.gowpet.pos.billing.receipt.controller;

import com.gowpet.pos.billing.receipt.ReceiptDataService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing/receipt")
public class ReceiptDataController {
    private final ReceiptDataService receiptSvc;

    ReceiptDataController(ReceiptDataService receiptSvc) {
        this.receiptSvc = receiptSvc;
    }

    @GetMapping
    ReceiptDataDto getReceiptData() {
        var data = receiptSvc.getReceiptData();

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
    ReceiptDataDto setReceiptData(@RequestBody ReceiptDataDto data) {
        receiptSvc.setReceiptData(data);
        /*
            No point to use the returned value of setReceiptData since we'll
            return the same data anyway, as long as the saving was successful.
         */
        return data;
    }
}

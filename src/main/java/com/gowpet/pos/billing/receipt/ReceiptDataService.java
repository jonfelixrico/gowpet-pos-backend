package com.gowpet.pos.billing.receipt;

import org.springframework.stereotype.Service;

@Service
public class ReceiptDataService {
    final private ReceiptDataRepository repo;

    private ReceiptDataService(ReceiptDataRepository repo) {
        this.repo = repo;
    }

    /**
     *
     * @return Null if there is no receipt data yet.
     */
    public ReceiptData getReceiptData() {
        var result = repo.findById(ReceiptData.DEFAULT_ID);
        return result.orElseGet(() -> null);
    }
}

package com.gowpet.pos.billing.receipt;

import lombok.*;
import lombok.experimental.SuperBuilder;
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
        return result.orElse(null);
    }

    public ReceiptData.ReceiptDataBuilder getBuilder() {
        var result = getReceiptData();
        if (result == null) {
            return ReceiptData.builder().id(ReceiptData.DEFAULT_ID);
        }

        return result.toBuilder();
    }

    public ReceiptData setReceiptData(ReceiptDataInput input) {
        var receiptData = getBuilder()
                .header(input.getHeader())
                .address(input.getAddress())
                .contactNo(input.getContactNo())
                .snsLink(input.getSnsLink())
                .snsMessage(input.getSnsMessage())
                .build();

        return repo.save(receiptData);
    }

    @SuperBuilder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    @NoArgsConstructor
    public static class ReceiptDataInput {
        private String header;
        private String[] address;
        private String contactNo;

        private String snsLink;
        private String snsMessage;
    }
}

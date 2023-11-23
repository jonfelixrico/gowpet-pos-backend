package com.gowpet.pos.billing.receipt;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<ReceiptData> getReceiptData() {
        return repo.findById(ReceiptData.DEFAULT_ID);
    }

    public ReceiptData.ReceiptDataBuilder getBuilder() {
        var result = getReceiptData();
        if (result.isEmpty()) {
            return ReceiptData.builder().id(ReceiptData.DEFAULT_ID);
        }

        return result.get().toBuilder();
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

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    public static class ReceiptDataInput {
        private String header;
        private String address;
        private String contactNo;

        private String snsLink;
        private String snsMessage;
    }
}

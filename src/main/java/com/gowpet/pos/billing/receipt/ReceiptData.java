package com.gowpet.pos.billing.receipt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
public class ReceiptData {
    @Id
    private String id;

    /**
     * The text in bold at the top of the receipt
     */
    private String header;

    /**
     * We're using a string array since addresses can have multiple lines
     */
    private String[] address;
    private String contactNo;

    /**
     * Social media site URL
     */
    private String snsLink;

    /**
     * Social media site message
     */
    private String snsMessage;
}

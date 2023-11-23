package com.gowpet.pos.billing.receipt.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class ReceiptDataDto {
    private String header;
    private String[] address;
    private String contactNo;

    private String snsLink;
    private String snsMessage;
}

package com.gowpet.pos.billing.receipt.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class ReceiptDataDto {
    private String header;
    private String address;
    private String contactNo;

    private String snsLink;
    private String snsMessage;
}


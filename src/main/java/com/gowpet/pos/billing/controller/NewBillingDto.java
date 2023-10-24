package com.gowpet.pos.billing.controller;

import com.gowpet.pos.billing.service.BillingService.NewBilling;

import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
public class NewBillingDto extends NewBilling {
}

package com.gowpet.pos.billing.service;

public record AggregatedBillingItem (String catalogItemId, Double price, Long quantity) {
}

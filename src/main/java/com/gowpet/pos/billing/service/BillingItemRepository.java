package com.gowpet.pos.billing.service;

import org.springframework.data.repository.CrudRepository;

interface BillingItemRepository extends CrudRepository<BillingItem, String> {
}

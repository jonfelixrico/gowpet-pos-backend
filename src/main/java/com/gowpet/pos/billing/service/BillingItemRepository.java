package com.gowpet.pos.billing.service;

import org.springframework.data.repository.CrudRepository;

public interface BillingItemRepository extends CrudRepository<BillingItemDb, String> {
}

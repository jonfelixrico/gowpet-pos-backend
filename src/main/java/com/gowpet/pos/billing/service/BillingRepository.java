package com.gowpet.pos.billing.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface BillingRepository extends CrudRepository<Billing, String>, PagingAndSortingRepository<Billing, String> {
}

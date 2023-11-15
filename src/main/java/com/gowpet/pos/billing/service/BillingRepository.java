package com.gowpet.pos.billing.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

interface BillingRepository extends CrudRepository<Billing, String>, PagingAndSortingRepository<Billing, String> {
    Optional<Billing> findTopByOrderBySerialNoDesc();
}

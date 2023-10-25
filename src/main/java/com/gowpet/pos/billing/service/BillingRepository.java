package com.gowpet.pos.billing.service;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

interface BillingRepository extends CrudRepository<Billing, String>, JpaSpecificationExecutor<Billing> {
}

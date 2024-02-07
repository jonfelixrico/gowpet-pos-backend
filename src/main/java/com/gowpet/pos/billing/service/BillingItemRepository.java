package com.gowpet.pos.billing.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface BillingItemRepository extends CrudRepository<String, BillingItem> {
    @Query("""
        SELECT b
        FROM BillingItem AS b
        WHERE
            b.billing.createDt BETWEEN ?1 AND ?2
    """)
    List<BillingItem> listItems(Instant start, Instant end);
}

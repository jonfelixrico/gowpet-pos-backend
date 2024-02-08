package com.gowpet.pos.billing.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface BillingItemRepository extends CrudRepository<BillingItem, String> {
    @Query("""
        SELECT new com.gowpet.pos.billing.service.AggregatedBillingItem(bi.catalogItem.id, bi.price, COUNT(1))
        FROM BillingItem AS bi
        WHERE
            bi.billing.createDt BETWEEN ?1 AND ?2
        GROUP BY bi.catalogItem.id, bi.price
    """)
    List<AggregatedBillingItem> aggregateItems(Instant start, Instant end);
}

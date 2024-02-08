package com.gowpet.pos.billing.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BillingItemRepository extends CrudRepository<BillingItem, String> {
    @Query("""
        SELECT new com.gowpet.pos.billing.service.AggregatedBillingItem(bi.catalogItem.id, bi.price, SUM(bi.quantity))
        FROM BillingItem AS bi
        GROUP BY bi.catalogItem.id, bi.price
    """)
    List<AggregatedBillingItem> aggregateItems(@Param("start") Instant start, @Param("end") Instant end);
}

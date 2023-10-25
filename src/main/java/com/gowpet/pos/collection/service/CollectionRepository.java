package com.gowpet.pos.collection.service;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

interface CollectionRepository extends CrudRepository<Collection, String>, JpaSpecificationExecutor<Collection> {
}

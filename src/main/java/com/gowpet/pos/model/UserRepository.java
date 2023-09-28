package com.gowpet.pos.model;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

}

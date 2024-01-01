package com.gowpet.pos.user.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

interface UserRepository extends CrudRepository<User, UUID>, PagingAndSortingRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}

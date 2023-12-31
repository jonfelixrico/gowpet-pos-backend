package com.gowpet.pos.user.service;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);
}

package com.gowpet.pos.user.service;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, UUID> {
	User findByUsername(String username);
}

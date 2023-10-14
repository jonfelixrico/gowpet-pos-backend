package com.gowpet.pos.user.service;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {
	public User findByUsername(String username);
}

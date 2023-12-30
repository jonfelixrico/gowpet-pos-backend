package com.gowpet.pos.user.service;

import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}

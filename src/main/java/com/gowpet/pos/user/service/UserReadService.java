package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserReadService {
    final UserRepository repo;

    UserReadService(UserRepository repo) {
        this.repo = repo;
    }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }
}

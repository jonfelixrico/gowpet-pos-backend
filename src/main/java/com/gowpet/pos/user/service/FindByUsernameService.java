package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The sole purpose of this service is to expose the findByUsername method to the authentication-related code.
 * The reason why we're not using UserService in the authentication-related code is because of the password encoder
 * that we're injecting into UserService. Using UserService in the authentication-related code will cause a cyclical
 * dependency.
 */
@Service
public class FindByUsernameService {
    private final UserRepository repo;

    FindByUsernameService(UserRepository repo) {
        this.repo = repo;
    }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }
}

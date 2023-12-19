package com.gowpet.pos.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder pwEncoder;

    UserService(UserRepository repo, PasswordEncoder pwEncoder) {
        this.repo = repo;
        this.pwEncoder = pwEncoder;
    }

    public void create(String username, String password) {
        repo.save(User.builder()
                .username(username)
                .password(pwEncoder.encode((password)))
                .build());
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}

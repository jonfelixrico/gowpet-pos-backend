package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;

    UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void create(String username, String password, User createBy) {
        var toSave = User.builder()
                .username(username)
                .password(password)
                .createBy(createBy)
                .build();

        repo.save(toSave);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}

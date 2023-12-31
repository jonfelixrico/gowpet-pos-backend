package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;


@Service
public class UserService extends UserReadService {
    UserService(UserRepository repo) {
        super(repo);
    }

    public void create(String username, String password, User createBy) {
        var toSave = User.builder()
                .username(username)
                .password(password)
                .createBy(createBy)
                .build();

        repo.save(toSave);
    }
}

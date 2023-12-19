package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;

@Service
public class RootUserSetupService {
    private final UserRepository userRepo;

    RootUserSetupService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean hasRootUserBeenSetUp () {
        return userRepo.count() > 0;
    }

    public void createRootUser(String username, String password) {
        if (hasRootUserBeenSetUp()) {
            throw new IllegalStateException();
        }

        userRepo.save(User.builder()
                .username(username)
                .password(password).build());
    }
}

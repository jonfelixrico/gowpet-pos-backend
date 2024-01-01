package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;

@Service
public class RootUserSetupService {
    private final UserRepository userRepo;
    private final UserService userSvc;

    RootUserSetupService(UserRepository userRepo, UserService userSvc) {
        this.userRepo = userRepo;
        this.userSvc = userSvc;
    }

    public boolean hasRootUserBeenSetUp() {
        return userRepo.count() > 0;
    }

    public void createRootUser(String username, String password) {
        if (hasRootUserBeenSetUp()) {
            throw new IllegalStateException();
        }

        userSvc.create(username, password, null);
    }
}

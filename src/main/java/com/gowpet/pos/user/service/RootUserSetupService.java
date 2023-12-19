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
}

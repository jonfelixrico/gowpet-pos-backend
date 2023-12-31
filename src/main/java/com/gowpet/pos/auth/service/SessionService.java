package com.gowpet.pos.auth.service;

import com.gowpet.pos.user.service.FindByUsernameService;
import com.gowpet.pos.user.service.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {
    private final FindByUsernameService svc;

    SessionService(FindByUsernameService svc) {
        this.svc = svc;
    }

    public Optional<User> getSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // User is not authenticated
        if (authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return svc.findByUsername(authentication.getName());
    }
}

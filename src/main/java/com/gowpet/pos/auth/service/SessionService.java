package com.gowpet.pos.auth.service;

import com.gowpet.pos.user.service.FindByUsernameService;
import com.gowpet.pos.user.service.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SessionService {
    private final FindByUsernameService svc;

    SessionService(FindByUsernameService svc) {
        this.svc = svc;
    }

    public Optional<User> getSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // User is not authenticated
        if (authentication instanceof AnonymousAuthenticationToken) {
            log.warn("getSessionUser was used in an unauthenticated context. Please check for a possible misuse of the method.");
            return Optional.empty();
        }

        return svc.findByUsername(authentication.getName());
    }
}

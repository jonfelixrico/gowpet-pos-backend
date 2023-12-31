package com.gowpet.pos.user.controller;

import com.gowpet.pos.auth.service.SessionService;
import com.gowpet.pos.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final SessionService sessionSvc;
    private final UserService userSvc;

    UserController(SessionService sessionSvc, UserService userSvc) {
        this.sessionSvc = sessionSvc;
        this.userSvc = userSvc;
    }

    @PostMapping
    void createUser(@RequestBody CreateUserDto newUser) {
        var sessionUser = sessionSvc.getSessionUser().orElseThrow();
        userSvc.create(newUser.getUsername(), newUser.getPassword(), sessionUser);
        log.info("Created user {}", newUser.getUsername());
    }
}

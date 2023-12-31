package com.gowpet.pos.user.controller;

import com.gowpet.pos.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userSvc;

    UserController(UserService userSvc) {
        this.userSvc = userSvc;
    }

    @PostMapping
    void createUser(@RequestBody CreateUserDto newUser, @AuthenticationPrincipal UserDetails user) {
        userSvc.create(newUser.getUsername(), newUser.getPassword(), userSvc.findByUsername(user.getUsername()).orElseThrow());
        log.info("Created user {}", newUser.getUsername());
    }
}

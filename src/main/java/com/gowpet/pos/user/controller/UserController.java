package com.gowpet.pos.user.controller;

import com.gowpet.pos.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userSvc;
    private final PasswordEncoder pwEncoder;

    UserController(UserService userSvc, PasswordEncoder pwEncoder) {
        this.userSvc = userSvc;
        this.pwEncoder = pwEncoder;
    }

    @PostMapping
    void createUser(@RequestBody CreateUserDto newUser, @AuthenticationPrincipal UserDetails user) {
        userSvc.create(newUser.getUsername(), pwEncoder.encode(newUser.getPassword()), userSvc.findByUsername(user.getUsername()));
    }
}

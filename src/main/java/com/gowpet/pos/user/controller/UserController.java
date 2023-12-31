package com.gowpet.pos.user.controller;

import com.gowpet.pos.auth.service.SessionService;
import com.gowpet.pos.user.service.User;
import com.gowpet.pos.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .build();
    }

    @GetMapping
    ResponseEntity<List<UserDto>> listUsers(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "15") Integer itemCount) {
        var page = userSvc.list(pageNo, itemCount);

        return ResponseEntity.ok()
                .header("X-Total-Count", Integer.toString(page.getTotalPages()))
                .body(page.stream().map(this::toDto).toList());
    }
}

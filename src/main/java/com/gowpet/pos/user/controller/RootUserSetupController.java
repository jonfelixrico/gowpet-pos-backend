package com.gowpet.pos.user.controller;

import com.gowpet.pos.user.service.RootUserSetupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/root")
public class RootUserSetupController {
    private final RootUserSetupService rootUserSetupSvc;

    RootUserSetupController(RootUserSetupService rootUserSetupSvc) {
        this.rootUserSetupSvc = rootUserSetupSvc;
    }

    @GetMapping
    Map<String, Boolean> hasRootUserBeenSetUp() {
        return Map.of("hasRootUserBeenSetUp", rootUserSetupSvc.hasRootUserBeenSetUp());
    }

    @PostMapping
    void createRootUser(@RequestBody CreateUserDto rootUser) {
        rootUserSetupSvc.createRootUser(
                rootUser.getUsername(),
                rootUser.getPassword());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<Object> handleIllegalRootUserCreateAttempt() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

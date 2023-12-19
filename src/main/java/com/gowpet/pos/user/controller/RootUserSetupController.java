package com.gowpet.pos.user.controller;

import com.gowpet.pos.user.service.RootUserSetupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/root")
public class RootUserSetupController {
    private final RootUserSetupService rootUserSetupSvc;
    private final PasswordEncoder pwEncoder;

    RootUserSetupController(RootUserSetupService rootUserSetupSvc, PasswordEncoder pwEncoder) {
        this.rootUserSetupSvc = rootUserSetupSvc;
        this.pwEncoder = pwEncoder;
    }

    @GetMapping
    Map<String, Boolean> hasRootUserBeenSetUp() {
        return Map.of("hasRootUserBeenSetUp", rootUserSetupSvc.hasRootUserBeenSetUp());
    }

    @PostMapping
    void createRootUser(@RequestBody CreateUserDto rootUser) {
        rootUserSetupSvc.createRootUser(
                rootUser.getUsername(),
                /*
                    We can't do password encoding at the service level since injecting PwEncoder from the
                    service level will cause a circular dependency error. The app won't start.
                 */
                pwEncoder.encode(rootUser.getPassword()));
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<Object> handleIllegalRootUserCreateAttempt() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

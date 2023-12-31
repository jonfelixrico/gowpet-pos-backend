package com.gowpet.pos.user.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder pwEncoder;

    UserService(UserRepository repo, PasswordEncoder pwEncoder) {
        this.repo = repo;
        this.pwEncoder = pwEncoder;
    }

    public void create(String username, String password, User createBy) {
        var toSave = User.builder()
                .username(username)
                .password(pwEncoder.encode(password))
                .createBy(createBy)
                .build();

        repo.save(toSave);
    }

    public List<User> list(int pageNo, int size) {
        return repo.findAll(PageRequest.of(pageNo, size)).toList();
    }
}

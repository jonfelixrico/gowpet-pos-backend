package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UserRepository repo;
	
	UserService(UserRepository repo) {
		this.repo = repo;
	}

	public void create(String username, String password) {
		repo.save(User.builder()
				.username(username)
				.password(password).build());
	}
	
	public User findByUsername(String username) {
		return repo.findByUsername(username);
	}
}

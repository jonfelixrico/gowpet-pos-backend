package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UserRepository repo;
	
	UserService(UserRepository repo) {
		this.repo = repo;
	}

	public User createUser (String username, String password) {
		return repo.save(User.builder()
				.username(username)
				.password(password).build());
	}
	
	public User getUserByUsername(String username) {
		return repo.findByUsername(username);
	}
}

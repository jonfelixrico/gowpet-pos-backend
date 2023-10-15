package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UserRepository repo;
	
	UserService(UserRepository repo) {
		this.repo = repo;
	}

	public User createUser (String username, String password) {
		return repo.save(new User(username, password));
	}
	
	public User getUserByUsername(String username) {
		return repo.findByUsername(username);
	}
}

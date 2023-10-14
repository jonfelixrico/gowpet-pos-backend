package com.gowpet.pos.user.service;

import org.springframework.stereotype.Service;

@Service
class ImplUserService extends UserService {
	private UserRepository repo;
	
	ImplUserService(UserRepository repo) {
		this.repo = repo;
	}

	public User createUser (String username, String password) {
		return repo.save(new User(username, password));
	}
	
	public User getUserByUsername(String username) {
		return repo.findByUsername(username);
	}
}

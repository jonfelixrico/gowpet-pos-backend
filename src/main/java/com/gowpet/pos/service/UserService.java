package com.gowpet.pos.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gowpet.pos.model.user.User;
import com.gowpet.pos.model.user.UserRepository;

@Service
public class UserService {
	private UserRepository repo;
	private PasswordEncoder pwEncoder;
	
	public User createUser (String username, String password) {
		return repo.save(new User(username, pwEncoder.encode(password)));
	}
	
	public User getUserByUsername(String username) {
		return repo.findByUsername(username);
	}
}

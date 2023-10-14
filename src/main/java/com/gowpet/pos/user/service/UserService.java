package com.gowpet.pos.user.service;

public abstract class UserService {
	public abstract User createUser (String username, String password);
	
	public abstract User getUserByUsername(String username);
}

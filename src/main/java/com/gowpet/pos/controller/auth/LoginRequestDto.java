package com.gowpet.pos.controller.auth;

public class LoginRequestDto {
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public LoginRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	
}

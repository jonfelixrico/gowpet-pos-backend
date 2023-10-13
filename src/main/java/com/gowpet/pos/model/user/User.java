package com.gowpet.pos.model.user;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
// Can't use "user" since it's a reserved keyword in some DBs (pg)
@Table(name = "app_user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	protected User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UUID getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}

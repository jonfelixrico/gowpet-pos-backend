package com.gowpet.pos.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	private String username;
	
	public UUID getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
}

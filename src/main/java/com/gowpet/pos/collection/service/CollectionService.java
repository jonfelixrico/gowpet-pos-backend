package com.gowpet.pos.collection.service;

import org.springframework.stereotype.Service;

@Service
public class CollectionService {
	private CollectionRepository repo;

	CollectionService(CollectionRepository repo) {
		this.repo = repo;
	}
}

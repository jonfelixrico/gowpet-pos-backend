package com.gowpet.pos.auth.service;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class RsaService {
	KeyPair keyPair = null;
	
	private KeyPair getKeyPair() throws NoSuchAlgorithmException {
		if (keyPair == null) {
			var gen = KeyPairGenerator.getInstance("RSASSA-PSS");
			keyPair = gen.generateKeyPair();
		}
		
		return keyPair;
	}
	
	public Key getPublicKey() throws NoSuchAlgorithmException {
		return getKeyPair().getPublic();
	}
	
	public Key getPrivateKey() throws NoSuchAlgorithmException {
		return getKeyPair().getPrivate();
	}
}

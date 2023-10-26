package com.gowpet.pos.auth.service;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class RsaService {
	KeyPair keyPair = null;
	
	private KeyPair getKeyPair() {
		if (keyPair == null) {
			try {
				var gen = KeyPairGenerator.getInstance("RSASSA-PSS");
				gen.initialize(2048);
				keyPair = gen.generateKeyPair();
			} catch (NoSuchAlgorithmException e) {
				// We're suppressing this because this is unlikely to happen

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return keyPair;
	}
	
	public Key getPublicKey() {
		return getKeyPair().getPublic();
	}
	
	public Key getPrivateKey() {
		return getKeyPair().getPrivate();
	}
}

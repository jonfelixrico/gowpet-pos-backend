package com.gowpet.pos.auth.service;

import java.io.IOException;
import java.io.StringReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RsaService {
	private String privateKeyPem;
	private String publicKeyPem;
	private Key publicKey;
	private Key privateKey;

	RsaService(@Value("${app.rsa.private:}") String privateKeyPem,
			@Value("${app.rsa.publicX509:}") String publicKeyPem) {
		this.privateKeyPem = privateKeyPem;
		this.publicKeyPem = publicKeyPem;
	}

	public Key getPublicKey() {
		if (publicKey == null) {
			try (var stringReader = new StringReader(publicKeyPem)) {
				var pemReader = new PemReader(stringReader);
				var pemObject = pemReader.readPemObject();
				var content = pemObject.getContent();
				X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
				
				KeyFactory factory = KeyFactory.getInstance("RSA");
				publicKey = factory.generatePublic(pubKeySpec);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return publicKey;
	}

	public Key getPrivateKey() {
		if (privateKey == null) {
			try (var stringReader = new StringReader(privateKeyPem)) {
				var pemReader = new PemReader(stringReader);
			    var pemObject = pemReader.readPemObject();
			    byte[] content = pemObject.getContent();
			    PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
			    
			    KeyFactory factory = KeyFactory.getInstance("RSA");
			    privateKey = factory.generatePrivate(privKeySpec);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return privateKey;
	}
}

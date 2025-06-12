package com.example.hospital_project;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EN_DE {
	private static final String type = "AES";
	private static final byte[] Key = "MySecretKey12345".getBytes();
	
	public static String encrypt(String data) throws Exception{
		Cipher cipher = Cipher.getInstance(type);
		SecretKeySpec keySpec = new SecretKeySpec(Key, type);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] encrypted = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public static String decrypt(String encryptedData) throws Exception{
		Cipher cipher = Cipher.getInstance(type);
		SecretKeySpec keySpec = new SecretKeySpec(Key,type);
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] decoded = Base64.getDecoder().decode(encryptedData);
		byte[] decrypted = cipher.doFinal(decoded);
		return new String(decrypted);
		
	}
}

package com.bci.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Properties;

public final class Encryption {

	private static final Properties props = ApplicationProperties.loadProperties();
	private static final String password = props.getProperty("keyPass");

	public static String encrypt (String message) throws Exception {
		if (password!=null && !password.isEmpty()) {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			final byte[] digestOfPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			System.arraycopy(keyBytes, 0, keyBytes, 16, 8);
			final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			final byte[] plainTextBytes = message.getBytes(StandardCharsets.UTF_8);
			final byte[] cipherText = cipher.doFinal(plainTextBytes);
			String base64String = Base64.getEncoder().encodeToString(cipherText);
			return base64String;
		}
		return null;
	}

	public static String decrypt (String encryptedText) throws Exception {
		if (password!=null && !password.isEmpty()) {
		    final MessageDigest md = MessageDigest.getInstance("MD5");
		    final byte[] digestOfPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
		    final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		    System.arraycopy(keyBytes, 0, keyBytes, 16, 8);
		    final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		    final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		    final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		    decipher.init(Cipher.DECRYPT_MODE, key, iv);
		    final byte[] cipherText = Base64.getDecoder().decode(encryptedText);
		    final byte[] plainTextBytes = decipher.doFinal(cipherText);
		    String plainText = new String(plainTextBytes, StandardCharsets.UTF_8);
		    return plainText;
		}
		return ""; 
	}
}


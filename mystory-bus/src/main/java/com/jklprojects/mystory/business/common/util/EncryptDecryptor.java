/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Singleton AES 128 bit Encryptor and Decryptor.
 *
 * <p>
 * Requires JDK 1.8+ {@link java.util.Base64}
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Aug 14, 2015
 * @version 2, 2017-Dec-23
 */
public class EncryptDecryptor {
	private static final XLogger logger = XLoggerFactory.getXLogger(EncryptDecryptor.class);

	private static final String AES_ALGO_NAME = "AES";
	private static EncryptDecryptor instance;

	/** DO NOT CHANGE the key! */
	private static final String ENCODED_128_BIT_KEY = "EuIfFp1V3r1Y5M0Yb6mmHQ==";

	private Key key128BitAES;
	private Cipher cipher;
	private Cipher decipher;

	/** private constructor - initialize cipher, decipher and key */
	private EncryptDecryptor() {
		try {
			byte[] decodedKey = Base64.getDecoder().decode(ENCODED_128_BIT_KEY);
			key128BitAES = new SecretKeySpec(decodedKey, 0, decodedKey.length, AES_ALGO_NAME);

			cipher = Cipher.getInstance(AES_ALGO_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, key128BitAES);

			decipher = Cipher.getInstance(AES_ALGO_NAME);
			decipher.init(Cipher.DECRYPT_MODE, key128BitAES);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			logger.catching(e);
			e.printStackTrace();
		}
	}

	/** @return an instance of this class */
	public static EncryptDecryptor getInstance() {
		if (instance == null) {
			instance = new EncryptDecryptor();
		}
		return instance;
	}

	/**
	 * Encrypt the text and return it.
	 *
	 * @return encrypted text
	 */
	public String encrypt(String text) {
		try {
			byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			logger.catching(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Return the decrypted string.
	 *
	 * @param encrypted
	 * @return decrypted string.
	 */
	public String decrypt(String encrypted) {
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encrypted);
			return new String(decipher.doFinal(decodedBytes), StandardCharsets.UTF_8);
		} catch (Exception e) {
			logger.catching(e);
			e.printStackTrace();
		}
		return null;
	}

	/** @return 128 bit encoded key */
	public String generate128BitAESEncodedKey() {
		return generate128BitEncodedKey(128);
	}

	private String generate128BitEncodedKey(int bitSize) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(AES_ALGO_NAME);
			keyGen.init(bitSize);

			SecretKey secretKey = keyGen.generateKey();
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());

		} catch (NoSuchAlgorithmException e) {
			logger.catching(e);
			e.printStackTrace();
		}
		return null;
	}
}

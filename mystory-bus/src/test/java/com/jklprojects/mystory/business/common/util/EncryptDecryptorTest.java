/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-08-14
 * @version 1, 2017-01-23
 * @version 2, 2017-12-23
 * @version 2, 2018-04-23
 */
@DisplayName("Encrypt Decryptor Test")
class EncryptDecryptorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test Encrypt")
	void testEncrypt() {
		String str = "Hello World";
		String encrypted = EncryptDecryptor.getInstance().encrypt(str);
		System.out.println("testEncrypt: " + encrypted);
		assertNotNull(encrypted);
		assertNotEquals(encrypted, str);
	}

	@Test
	@DisplayName("Test Decrypt")
	void testDecrypt() {
		String str = "Hello World";
		String encrypted = EncryptDecryptor.getInstance().encrypt(str);
		String decrypted = EncryptDecryptor.getInstance().decrypt(encrypted);
		System.out.println("encrypted: " + encrypted);
		System.out.println("decrypted: " + decrypted);
		assertEquals(str, decrypted);
	}

	@Test
	@DisplayName("Test Encrypt Decrypt")
	void testEncryptDecrypt() {
		String str = "testEncrypt2";
		System.out.println("encrypting: " + str);
		String encrypted = EncryptDecryptor.getInstance().encrypt(str);
		System.out.println("encrypted: " + encrypted);
		String decrypted = EncryptDecryptor.getInstance().decrypt(encrypted);
		System.out.println("decrypted: " + decrypted);
		assertEquals(str, decrypted);
	}

	@Test
	@DisplayName("Test Generate 128 Bit AES Encoded Key")
	void testGenerate128BitAESEncodedKey() {
		String _128BitAESEncodedKey = EncryptDecryptor.getInstance().generate128BitAESEncodedKey();
		System.out.println(_128BitAESEncodedKey);
		assertNotNull(_128BitAESEncodedKey);
	}

	@Test
	@DisplayName("Test Decrypt _ 1")
	void testDecrypt_1() {
		String str = "testEncrypt2";
		String encrypted = "k7ZxwDPJruMQfrTNA3BNjw==";
		String decrypted = EncryptDecryptor.getInstance().decrypt(encrypted);
		System.out.println("decrypted: " + decrypted);
		assertEquals(str, decrypted);
	}

	@Test
	void testEncryptDecrypt_2() {
		String str = "f2bbe6af561242aafd3d99248dc7ad41663dc394847c3d19e7facc927a7c8c33-JUTxrLgWqBnhpPd1";
		System.out.println("encrypting: " + str);
		String encrypted = EncryptDecryptor.getInstance().encrypt(str);
		System.out.println("encrypted: " + encrypted);
		String decrypted = EncryptDecryptor.getInstance().decrypt(encrypted);
		System.out.println("decrypted: " + decrypted);
		assertEquals(str, decrypted);
	}

	@Test
	void testDecrypt_2() {
		String str = "f2bbe6af561242aafd3d99248dc7ad41663dc394847c3d19e7facc927a7c8c33-JUTxrLgWqBnhpPd1";
		String encrypted = "7mBlzyvp6sYw1+In9TFsoVNxsXcCHeONGfRDD5DI4m/XCXfWEiBhr1btYYwvRRR+dxi3+fGyIcwFvKGfcyyvOshU5jpTFq1gcxgZaK5d7nIE9j5vp2jrTMBo7uogyhQ0";
		String decrypted = EncryptDecryptor.getInstance().decrypt(encrypted);
		System.out.println("decrypted: " + decrypted);
		assertEquals(str, decrypted);
	}

	@Test
	void testEncryptDecrypt_3() {
		String str = "x1bQ8wHUOvzksPXh";
		System.out.println("encrypting: " + str);
		String encrypted = EncryptDecryptor.getInstance().encrypt(str);
		System.out.println("encrypted: " + encrypted);
		String decrypted = EncryptDecryptor.getInstance().decrypt(encrypted);
		System.out.println("decrypted: " + decrypted);
		assertEquals(str, decrypted);
	}
}

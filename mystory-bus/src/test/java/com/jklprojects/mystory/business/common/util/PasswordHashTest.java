/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-08-14
 * @version 1, 2017-01-23
 * @version 3, 2018-11-23
 * @version 3, 2018-12-23
 */
@DisplayName("Password Hash Test")
class PasswordHashTest {

	@Test
	@DisplayName("Test Password Hash 11")
	void testPasswordHash11() {
		String pwd1 = "mySecretPassw0rd";
		pwdTest(pwd1,
				"2015:891d1af330beb3d0408eeed9c2fe1c668d03ecf8cfac0590:0d35711de5614757ba58b7209d5235040c070422d5e02118");
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String password = "mySecretPassw0rd";
		String pwdHash = PasswordHash.createHash(password, PasswordHash.PBKDF2_ITERATIONS);
		System.out.println("Password Hash for  " + password + " is: " + pwdHash);
	}

	private void pwdTest(String password, String pwdHashCheck) {
		String pwdHash;
		try {
			pwdHash = PasswordHash.createHash(password, PasswordHash.PBKDF2_ITERATIONS);
			System.out.println("Password Hash for  " + password + " is: " + pwdHash);
			assertTrue(PasswordHash.validatePassword(password, pwdHashCheck));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	@DisplayName("Test Password With Salt")
	void testPasswordWithSalt() {
		String pwd1 = "mySecretPassw0rd";
		pwdTest(pwd1,
				"2015:891d1af330beb3d0408eeed9c2fe1c668d03ecf8cfac0590:0d35711de5614757ba58b7209d5235040c070422d5e02118");
		String salt = "891d1af330beb3d0408eeed9c2fe1c668d03ecf8cfac0590";
		String correctHash = "0d35711de5614757ba58b7209d5235040c070422d5e02118";
		try {
			Assertions.assertTrue(
					PasswordHash.validatePasswordWithSalt(pwd1, salt, correctHash, PasswordHash.PBKDF2_ITERATIONS));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}

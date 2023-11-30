/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.audit;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-05-23
 */
@DisplayName("Audit Type Test")
class AuditTypeTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test To Enum _ match Found")
	void testToEnum_matchFound() {
		Assertions.assertTrue(AuditType.ENTITY_CRUD == AuditType.toEnum(1));
		Assertions.assertTrue(AuditType.USER_AUTHENTICATION == AuditType.toEnum(2));
		Assertions.assertTrue(AuditType.USER_SIGN_OUT == AuditType.toEnum(3));
	}

	@Test
	@DisplayName("Test To Enum _ No Such Element")
	void testToEnum_NoSuchElement() {
		assertThrows(NoSuchElementException.class, () -> {
			AuditType.toEnum(99999);
		});
	}

	@Test
	@DisplayName("Test In _ all Values")
	void testIn_allValues() {
		Assertions.assertTrue(AuditType.ENTITY_CRUD.in(AuditType.values()));
	}

	@Test
	@DisplayName("Test In _ user Sign In Sign Out")
	void testIn_userSignInSignOut() {
		Assertions.assertTrue(AuditType.USER_AUTHENTICATION.in(AuditType.USER_AUTHENTICATION, AuditType.USER_SIGN_OUT));
	}

	@Test
	@DisplayName("Test In _ Notuser Sign In Sign Out")
	void testIn_NotuserSignInSignOut() {
		Assertions.assertFalse(AuditType.USER_AUTHENTICATION.in(AuditType.ENTITY_CRUD));
	}
}

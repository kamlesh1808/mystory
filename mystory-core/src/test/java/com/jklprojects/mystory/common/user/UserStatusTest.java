/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mystory
 * @since 4, 2020-08-23
 */
class UserStatusTest {

	@Test
	void test_in() {
		Assertions.assertTrue(UserStatus.ACTIVE.in(UserStatus.values()));

		assertFalse(UserStatus.ACTIVE.in(UserStatus.INACTIVE, UserStatus.LOCKED_MAX_INVALID_SIGNIN_ATTEMPTS));
	}
}
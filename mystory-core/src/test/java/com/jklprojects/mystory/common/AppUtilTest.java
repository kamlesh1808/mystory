/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common;

import com.jklprojects.mystory.common.util.AppUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2018-04-23
 */
@DisplayName("App Util Test")
class AppUtilTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test Gen Rand User UID _ length Equal To 19")
	void testGenRandUserUID_lengthEqualTo19() {
		for (int i = 0; i < 1000; i++) {
			final String userUID = String.valueOf(AppUtil.genRandUserUID());
			Assertions.assertTrue(String.valueOf(userUID).length() == 19);
		}
	}
}

/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-06-23
 */
@DisplayName("App Env Name Test")
class AppEnvNameTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test _ to App Env Name")
	void test_toAppEnvName() {
		Assertions.assertTrue(AppEnvName.UAT == AppEnvName.toAppEnvName("UAT"));
	}
}

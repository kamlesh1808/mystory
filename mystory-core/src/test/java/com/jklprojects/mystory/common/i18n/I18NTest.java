/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.i18n;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Oct-23
 */
@DisplayName("I 18 N Test")
class I18NTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test Get RB Using Base Name")
	void testGetRBUsingBaseName() {
		Assertions.assertNotNull(I18N.APP_ENUMS.getRBUsingBaseName());
	}
}

/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 3, 2018-09-23
 */
@DisplayName("Random String Util Test")
class RandomStringUtilTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test Generate Random String JDK 8")
	void testGenerateRandomStringJDK8() {
		String randomStr32 = RandomStringUtil.generateRandomStringJDK8(32);
		Assertions.assertTrue(randomStr32 != null && randomStr32.length() == 32);
	}

	@Test
	@DisplayName("Test Generate Random String JDK 7")
	void testGenerateRandomStringJDK7() {
		String randomStr32 = RandomStringUtil.generateRandomStringJDK7(32);
		Assertions.assertTrue(randomStr32 != null && randomStr32.length() == 32);
	}

	@Test
	@DisplayName("Test Generate Random Strings")
	void testGenerateRandomStrings() {
		List<String> randomStrings = RandomStringUtil.generateRandomStrings(10, 32);
		Assertions.assertTrue(randomStrings != null && randomStrings.size() == 10);
	}
}

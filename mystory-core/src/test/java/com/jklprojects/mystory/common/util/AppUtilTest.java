/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-21
 * @version 3, 2018-09-23
 */
@DisplayName("App Util Test")
class AppUtilTest {

	@Test
	@DisplayName("Test Gen Random Token")
	void testGenRandomToken() {
		int size = 6;
		for (int i = 0; i < 100; i++) {
			String token = NumUtil.genRandomToken(size);
			Assertions.assertEquals(size, token.length());
		}
	}

	@Test
	@DisplayName("Test Alpha Sort")
	void testAlphaSort() {
		List<String> list = new ArrayList<>();
		list.add("Canada");
		list.add("Cambodia");
		List<String> sortedList = list.parallelStream().sorted().collect(Collectors.toList());
		Assertions.assertTrue(sortedList.get(0).equals("Cambodia"));
		Assertions.assertTrue(sortedList.get(1).equals("Canada"));
	}

	@Test
	@DisplayName("Test Get Caller Method Name")
	void testGetCallerMethodName() {
		Assertions.assertEquals(AppUtil.getCallerMethodName(), "testGetCallerMethodName");
		Assertions.assertFalse("testGetCallerMethodNameZZZZ".equals(AppUtil.getCallerMethodName()));
	}
}

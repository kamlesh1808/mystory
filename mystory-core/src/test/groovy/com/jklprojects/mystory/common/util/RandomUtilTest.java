/** Copyright (c) 2015 - 2020 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
* @author Kamleshkumar N. Patel
* @version 3, 2018-09-23
*/
@DisplayName("Random Util Test")
class RandomUtilTest {

	@Disabled
	@Test
	@DisplayName("Test Gen Random Token _ Groovy")
	void testGenRandomToken_Groovy() {
		for (int i = 0; i < 10000; i++) {
			String randToken = new RandomUtil().genRandomToken(20);
			Assertions.assertTrue(randToken.length() == 20);
		}
	}
}

/**
 * Copyright (c) 2016 JKLProjects Inc. All Rights Reserved.
 */

package com.jklprojects.mystory.common.util

import java.security.SecureRandom;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Feb 21, 2016
 */
class RandomUtil {
	private static final SecureRandom secureRand = new SecureRandom();

	/**
	 * Return randomly generated token
	 *
	 * @param size
	 *            size of the random token to generate
	 * @return random token of the given size
	 * 
	 * @throws IllegalArgumentException if size is greater than 20.
	 */
	def String genRandomToken(size) {
		if (size > 20) {
			throw new IllegalArgumentException("size should be equal or less than 20")
		}

		return new BigInteger(130, secureRand).toString(32).substring(0, size)
	}
}
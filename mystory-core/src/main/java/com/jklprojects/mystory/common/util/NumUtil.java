/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Number Utility functionalities.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-19
 * @version 2, 2018-04-23
 */
public class NumUtil {

	private static final SecureRandom secureRand = new SecureRandom();

	/**
	 * Returns a psuedo-random number between min and max, inclusive. The difference
	 * between min and max can be at most <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min
	 * @param max
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int genRandInt(int min, int max) {
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		final int randomNum = secureRand.nextInt(max - min + 1) + min;

		return randomNum;
	}

	/**
	 * @param min
	 * @param max
	 * @return Long between min and max, inclusive.
	 */
	public static long genRandLong(long min, long max) {
		return min + (long) (Math.random() * (max - min));
	}

	/**
	 * Return randomly generated token of requested size.
	 *
	 * @param size
	 *            size of the random token to generate
	 * @return random token
	 */
	public static String genRandomToken(int size) throws IllegalArgumentException {
		if (size > 20) {
			throw new IllegalArgumentException("size should be equal or less than 20");
		}

		return new BigInteger(130, secureRand).toString(32).substring(0, size);
	}

	/**
	 * Returns true if the string is a number, false otherwise.
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (final NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static boolean isValidAppId(Long aLong) {
		return aLong != null && aLong > 0;
	}

	/**
	 * Return parsed integer or 0 if not able to parse it.
	 *
	 * @param str
	 *            string to parse
	 * @return parsed integer or 0 if not able to parse it.
	 */
	public static int parseInteger(String str) {
		try {
			return Integer.parseInt(str);
		} catch (final NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Return parsed integer or the dfaultValue if not able to parse it.
	 *
	 * @param str
	 *            string to parse
	 * @param defaultValue
	 * @return parsed integer or the dfaultValue if not able to parse it.
	 */
	public static int parseInteger(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (final NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * Return parsed long or 0 if not able to parse it.
	 *
	 * @param str
	 *            string to parse
	 * @return parsed long or 0 if not able to parse it.
	 */
	public static long parseLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (final NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Converts a byte array into a hexadecimal string.
	 *
	 * @param array
	 *            the byte array to convert
	 * @return a length*2 character string encoding the byte array
	 */
	public static String toHex(byte[] array) {
		final BigInteger bi = new BigInteger(1, array);
		final String hex = bi.toString(16);

		System.out.println("Hex is " + hex);
		final int paddingLength = array.length * 2 - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}
}

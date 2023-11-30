/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

/**
 * String Utility.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-29
 * @version 2, 2018-04-23
 * @version 2, 2018-05-23
 */
public class StringUtil {

	/**
	 * Append the pad char to the string to reach the minimum length.
	 *
	 * @param str
	 * @param minLength
	 * @param padChar
	 * @return
	 */
	public static String append(String str, int minLength, char padChar) {
		if (str.length() >= minLength) {
			return str;
		}
		StringBuilder sb = new StringBuilder(minLength);
		sb.append(str);
		for (int i = str.length(); i < minLength; i++) {
			sb.append(padChar);
		}
		return sb.toString();
	}

	/**
	 * @param str
	 * @return true if the string is null or is empty
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * @param str1
	 * @param str2
	 * @return
	 * @see StringUtil#equals(Object)
	 */
	public static boolean isEqual(String str1, String str2) {
		if (str1 != null) {
			return str1.equals(str2);
		}

		if (str2 != null) {
			return str2.equals(str1);
		}

		return true;
	}

	/**
	 * @param str
	 * @return true if the string is not null and is not empty
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && !str.isEmpty();
	}

	/**
	 * @param str1
	 * @param str2
	 * @return
	 * @see #isEqual(String, String)
	 */
	public static boolean isNotEqual(String str1, String str2) {
		return !isEqual(str1, str2);
	}

	/**
	 * Pre-append the pad char to the string to reach the minimum length.
	 *
	 * @param str
	 * @param minLength
	 * @param padChar
	 * @return
	 */
	public static String preAppend(String str, int minLength, char padChar) {
		if (str.length() >= minLength) {
			return str;
		}
		StringBuilder sb = new StringBuilder(minLength);
		for (int i = str.length(); i < minLength; i++) {
			sb.append(padChar);
		}
		sb.append(str);
		return sb.toString();
	}
}

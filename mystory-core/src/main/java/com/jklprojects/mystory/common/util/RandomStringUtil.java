/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import java.awt.event.KeyEvent;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Kamleshkumar N. Patel
 * @version 3, 2018-09-23
 */
public class RandomStringUtil {

	private static final String DIGITS = "0123456789";
	private static final String SEPCIAL_CHARS = "~=+%^*/()[]{}/!@#$?|";
	private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

	private static final String UPPERCASE_LETTERS_LOWERCASE_LETTERS_DIGITS_SEPCIAL_CHARS = UPPERCASE_LETTERS
			+ LOWERCASE_LETTERS + SEPCIAL_CHARS + DIGITS;

	/**
	 * Generate a random String of given length.
	 *
	 * @param stringLength
	 * @return
	 */
	public static String generateRandomStringJDK8(int stringLength) {
		List<String> result = new ArrayList<>();

		SecureRandom secureRandom = new SecureRandom();
		Consumer<String> appendChar = s -> result.add("" + s.charAt(secureRandom.nextInt(s.length())));
		appendChar.accept(DIGITS);
		appendChar.accept(SEPCIAL_CHARS);

		while (result.size() < stringLength) {
			appendChar.accept(UPPERCASE_LETTERS_LOWERCASE_LETTERS_DIGITS_SEPCIAL_CHARS);
		}

		Collections.shuffle(result, secureRandom);

		return String.join("", result);
	}

	/**
	 * Generate a random String of given length.
	 *
	 * @param stringLength
	 * @return
	 */
	public static String generateRandomStringJDK7(int stringLength) {
		char[] result = new char[stringLength];
		int charLength = UPPERCASE_LETTERS_LOWERCASE_LETTERS_DIGITS_SEPCIAL_CHARS.length();

		int index = 0;
		SecureRandom secureRandom = new SecureRandom();
		while (index < stringLength) {
			result[index++] = UPPERCASE_LETTERS_LOWERCASE_LETTERS_DIGITS_SEPCIAL_CHARS
					.charAt(secureRandom.nextInt(charLength));
		}

		Collections.shuffle(Arrays.asList(result), secureRandom);
		String resultStr = new String(result);

		return resultStr;
	}

	/**
	 * Generate a list of random String of given length.
	 *
	 * @param numberOfStrings
	 * @param stringLength
	 * @return
	 */
	public static List<String> generateRandomStrings(int numberOfStrings, int stringLength) {
		List<String> randomStrings = new ArrayList<>();

		for (int i = 0; i < numberOfStrings; i++) {
			randomStrings.add(generateRandomStringJDK7(stringLength));
		}

		return randomStrings;
	}

	/**
	 * @param c
	 * @return
	 */
	public static boolean isPrintableChar(char c) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		return !Character.isISOControl(c) && c != KeyEvent.CHAR_UNDEFINED && block != null
				&& block != Character.UnicodeBlock.SPECIALS;
	}
}

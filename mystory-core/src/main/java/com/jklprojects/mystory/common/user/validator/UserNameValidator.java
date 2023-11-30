/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Good user name validator using regex.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Aug 19, 2015
 */
public class UserNameValidator {
	private final Pattern pattern;
	private Matcher matcher;

	/**
	 *
	 *
	 * <pre>
	* ^				start of line
	* (?!\\d)			does not start with a digit
	* (?!_)			does not start with underscore
	* [a-zA-Z_0-9]		contains only lower and upper case letters, digits, and underscore
	* {8,20}			between 8 to 20 length inclusive
	* $				end of line
	 * </pre>
	 */
	private static final String USERNAME_PATTERN = "^(?!\\d)(?!_)[a-zA-Z_0-9]{8,20}$";

	private static UserNameValidator instance;

	private UserNameValidator() {
		pattern = Pattern.compile(USERNAME_PATTERN);
	}

	public static UserNameValidator getInstance() {
		if (instance == null) {
			instance = new UserNameValidator();
		}
		return instance;
	}

	/**
	 * Validate userName with regular expression
	 *
	 * @param userName
	 *            userName for validation
	 * @return true valid userName, false otherwise
	 */
	public boolean validate(final String userName) {
		matcher = pattern.matcher(userName);
		return matcher.matches();
	}
}

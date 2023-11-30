/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate password is a strong password using regex.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Aug 18, 2015
 */
public class StrongPasswordValidator {

	private final Pattern pattern;
	private Matcher matcher;

	/**
	 *
	 *
	 * <pre>
	* (?!.*\\s)			cannot contain space
	* (?=.*\\d)			at least one digit
	* (?=.*[a-z])			at least one lower case letter
	* (?=.*[A-Z])			at least one upper case letter
	* (?=.*[!@#$%^*])		at least one of the listed special character
	* (?=.*[!@#$%^*])		at least one of the listed special character
	* .{8,20}				between 8 to 20 of length inclusive
	 * </pre>
	 */
	private static final String PASSWORD_PATTERN = "(?!.*\\s)((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*]).{8,20})";

	private static StrongPasswordValidator instance;

	private StrongPasswordValidator() {
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	public static StrongPasswordValidator getInstance() {
		if (instance == null) {
			instance = new StrongPasswordValidator();
		}
		return instance;
	}

	/**
	 * Validate password with regular expression
	 *
	 * @param password
	 *            password for validation
	 * @return true valid password, false otherwise
	 */
	public boolean validate(final String password) {
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
}

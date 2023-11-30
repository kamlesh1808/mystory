/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar Patel
 * @version 1, 2015-MAR-31
 */
public class EMailValidator {
	private static final XLogger logger = XLoggerFactory.getXLogger(EMailValidator.class);

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
			+ "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$";

	private static EMailValidator instance;

	private final Pattern pattern;
	private Matcher matcher;

	private EMailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public static EMailValidator getInstance() {
		if (instance == null) {
			instance = new EMailValidator();
		}
		return instance;
	}

	public boolean isValid(String value) {
		logger.entry(value);

		matcher = pattern.matcher(value);
		return matcher.matches();
	}
}

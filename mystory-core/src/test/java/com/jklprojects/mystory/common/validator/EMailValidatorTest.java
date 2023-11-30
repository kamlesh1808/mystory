/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.validator;

import com.jklprojects.mystory.common.user.validator.EMailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * JUnit Test for EMailValidator
 *
 * @author Kamleshkumar Patel
 * @version 1 2015-APR-01
 * @see EMailValidator
 */
@DisplayName("E Mail Validator Test")
class EMailValidatorTest {

	@Test
	@DisplayName("Test Valid Email")
	void testValidEmail() {
		EMailValidator validator = EMailValidator.getInstance();
		Assertions.assertTrue(validator.isValid("first@first.com"));
		Assertions.assertTrue(validator.isValid("first.last@gmail.com"));
		Assertions.assertTrue(validator.isValid("me@you.me.net"));
	}

	@Test
	@DisplayName("Test In Valid Email")
	void testInValidEmail() {
		EMailValidator validator = EMailValidator.getInstance();
		Assertions.assertFalse(validator.isValid("first@first..com"));
		Assertions.assertFalse(validator.isValid("first.last@.gmail.com"));
		Assertions.assertFalse(validator.isValid("me123@gmail.b"));
		Assertions.assertFalse(validator.isValid("mysite.google.com"));
	}
}

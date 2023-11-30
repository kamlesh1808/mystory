/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.validator;

import com.jklprojects.mystory.common.user.validator.UserNameValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-08-19
 * @version 2, 2018-05-23
 */
@DisplayName("User Name Validator Test")
class UserNameValidatorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test Validate _ invalid User Name")
	void testValidate_invalidUserName() {
		UserNameValidator userNameVal = UserNameValidator.getInstance();
		// not long enough
		Assertions.assertEquals(false, userNameVal.validate("mypwd"));
		// too long
		Assertions.assertEquals(false, userNameVal.validate("mypwdmypwdmypwdmypwdA"));
		// does not start with a letter
		Assertions.assertEquals(false, userNameVal.validate("123456789"));
		// does not start with a letter
		Assertions.assertEquals(false, userNameVal.validate("123456789_"));
		// cannot contain special characters other than underscore
		Assertions.assertEquals(false, userNameVal.validate("A123456+"));
		// cannot contain space at the end
		Assertions.assertEquals(false, userNameVal.validate("A1234567 "));
		// cannot contain space at start
		Assertions.assertEquals(false, userNameVal.validate(" 123456789_A"));
		// cannot contain underscore at start
		Assertions.assertEquals(false, userNameVal.validate("_123456789A_"));
		// cannot contain underscore at start
		Assertions.assertEquals(false, userNameVal.validate("____________"));
	}

	@Test
	@DisplayName("Test Validate _ invalid User Name _")
	void testValidate_invalidUserName_() {
		UserNameValidator userNameVal = UserNameValidator.getInstance();
		// cannot contain special characters other than underscore
		Assertions.assertEquals(false, userNameVal.validate("A123456@Google"));
	}

	@Test
	@DisplayName("Test Validate _ valid User Name")
	void testValidate_validUserName() {
		UserNameValidator userNameVal = UserNameValidator.getInstance();
		Assertions.assertEquals(true, userNameVal.validate("A12345678"));
		Assertions.assertEquals(true, userNameVal.validate("Hellothere"));
		Assertions.assertEquals(true, userNameVal.validate("A123456_"));
		Assertions.assertEquals(true, userNameVal.validate("a123456789_A"));
		Assertions.assertEquals(true, userNameVal.validate("a___________"));
	}
}

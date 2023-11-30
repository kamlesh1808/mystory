/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.validator;

import com.jklprojects.mystory.common.user.validator.StrongPasswordValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Aug 18, 2015
 */
@DisplayName("Strong Password Validator Test")
class StrongPasswordValidatorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test Validate _ invalid Passwords")
	void testValidate_invalidPasswords() {
		StrongPasswordValidator pwdVal = StrongPasswordValidator.getInstance();
		// no digit, no uppper case, not special character
		Assertions.assertEquals(false, pwdVal.validate("java"));
		// no uppercase
		Assertions.assertEquals(false, pwdVal.validate("helllothere%123"));
		// no lower case
		Assertions.assertEquals(false, pwdVal.validate("12HELLOTHERE%"));
		// no digit
		Assertions.assertEquals(false, pwdVal.validate("HELLOThere%"));
		// no special character
		Assertions.assertEquals(false, pwdVal.validate("HELLOThere123"));
		// not long enough
		Assertions.assertEquals(false, pwdVal.validate("He1%"));
		// not long enough
		Assertions.assertEquals(false, pwdVal.validate("Hello$1"));
		// no special character
		Assertions.assertEquals(false, pwdVal.validate("Hello(12"));
		// too long
		Assertions.assertEquals(false, pwdVal.validate("HellloThere%123HellloThere%123"));
		// space is invalid
		Assertions.assertEquals(false, pwdVal.validate("helL %*7"));
	}

	@Test
	@DisplayName("Test Validate _ valid Passwords")
	void testValidate_validPasswords() {
		StrongPasswordValidator pwdVal = StrongPasswordValidator.getInstance();
		// exact length, with digit, with special character, with upper case,
		// with lowercase
		Assertions.assertEquals(true, pwdVal.validate("Hello$12"));
		// good length, with digit, with special characters, with upper cases,
		// with lowercases
		Assertions.assertEquals(true, pwdVal.validate("helLo%*7the$E"));
		Assertions.assertEquals(true, pwdVal.validate("Kamlesh1%"));
	}
}

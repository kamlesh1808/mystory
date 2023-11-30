/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import com.jklprojects.mystory.common.ex.AppCodeException;

/**
 * Exceptions related to User Account.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Apr 17, 2015
 * @version 2, 2017-Dec-23
 */
public enum UserExceptions {
	USER_ACCOUNT_001("USER_ACCOUNT_001", "Current Password is incorrect. Password was not changed"), USER_ACCOUNT_002(
			"USER_ACCOUNT_002",
			"The User Name {0} is already in use and is not available."), USER_ACCOUNT_003("USER_ACCOUNT_003",
					"An existing user is already registered with the E-Mail {0}."), USER_ACCOUNT_005("USER_ACCOUNT_005",
							"You  have reached the maximum number {0} of sign-in attempts. Your account is locked. You may make a request for account recovery."), USER_ACCOUNT_004(
									"USER_ACCOUNT_004", "User not found."), USER_ACCOUNT_006("USER_ACCOUNT_006",
											"The account recovery duration of {0} minutes has expired. You may make another request for account recovery."), USER_ACCOUNT_007(
													"USER_ACCOUNT_007",
													"Error occured trying to set password."), USER_ACCOUNT_008(
															"USER_ACCOUNT_008",
															"User with User Name {0} was not found"), USER_ACCOUNT_009(
																	"USER_ACCOUNT_009",
																	"Invalid email {0}"), USER_ACCOUNT_010(
																			"USER_ACCOUNT_010",
																			"An existing user is already registered with the E-Mail {0}, User Name {1} and User Type {2}."), USER_ACCOUNT_011(
																					"USER_ACCOUNT_011",
																					"User with User UID {0} was not found"), USER_ACCOUNT_012(
																							"USER_ACCOUNT_012",
																							"Update on this type of user is not allowed");

	private String errorCode;
	private final String defaultMsg;

	UserExceptions(String errorCode, String defaultMsg) {
		this.errorCode = errorCode;
		this.defaultMsg = defaultMsg;
	}

	public String getDefaultMsg() {
		return defaultMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public AppCodeException newAppCodeException() {
		return new AppCodeException(getErrorCode(), getDefaultMsg());
	}

	public AppCodeException newAppCodeException(Object... params) {
		return new AppCodeException(getErrorCode(), getDefaultMsg()).setMessageParams(params);
	}

}

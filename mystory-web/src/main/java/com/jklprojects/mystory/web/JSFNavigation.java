/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web;

/**
 * JSF Navigation constants
 *
 * @author Kamleshkumar Patel
 * @version 1, 2015-MAR-31
 */
public enum JSFNavigation {
	SUCCESS("success"), FAILURE("failure"), SIGNIN("signin"), SIGNOUT_SUCCESS("signout-success"), SIGNOUT_FAILURE(
			"signout-failure"), NO_RESULTS("no_results"), ACCOUNT_RECOVERY("account_recovery"), CANCEL("cancel");

	private final String navigation;

	JSFNavigation(String navigation) {
		this.navigation = navigation;
	}

	public String getNavigation() {
		return navigation;
	}
}

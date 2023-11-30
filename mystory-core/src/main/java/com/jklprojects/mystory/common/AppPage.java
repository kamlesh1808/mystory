/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common;

import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-06-23
 * @since 2.18.06.23
 */
public enum AppPage {
	ABOUT_US(1, "About Us"), CONTACT_US(2, "Contact Us"), SIGN_UP(3, "Sign Up"), SIGN_IN(4,
			"Sign In"), TERMS_OF_SERVICE(5, "Terms of Service"), SECURITY_POLICY(6, "Security Policy"), PRIVACY_POLICY(
					7, "Privacy Policy"), ACKNOWLEDGEMENTS(8, "Acknowledgements"), REST_API(9, "REST API"), SPONSORS(10,
							"Sponsors"), DONATE(11, "Donate"), ACCOUNT_RECOVERY(12, "Account Recovery"), TAG_STORIES(13,
									"Tag Stories"), HOME_PAGE(14, "Home Page"), SUPPORT_US(15,
											"Support Us"), STORY_NAME_STORIES(16, "Story Name Stories");

	private final int id;
	private final String name;

	AppPage(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static AppPage toEnum(int id) {
		return Arrays.stream(AppPage.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isAboutUs() {
		return this == ABOUT_US;
	}

	public boolean isAcknowledgements() {
		return this == ACKNOWLEDGEMENTS;
	}

	public boolean isContactUs() {
		return this == CONTACT_US;
	}

	public boolean isDonate() {
		return this == DONATE;
	}

	public boolean isPrivacyPolicy() {
		return this == PRIVACY_POLICY;
	}

	public boolean isRESTAPI() {
		return this == REST_API;
	}

	public boolean isSecurityPolicy() {
		return this == SECURITY_POLICY;
	}

	public boolean isSignIn() {
		return this == SIGN_IN;
	}

	public boolean isSignUp() {
		return this == SIGN_UP;
	}

	public boolean isSponsors() {
		return this == SPONSORS;
	}

	public boolean isTermsOfService() {
		return this == TERMS_OF_SERVICE;
	}

	public boolean isSupportUs() {
		return this == AppPage.SUPPORT_US;
	}
}

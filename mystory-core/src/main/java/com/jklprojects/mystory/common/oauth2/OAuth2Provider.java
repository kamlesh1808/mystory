/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.oauth2;

/**
 * Represents a list of OAuth2 Providers.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Dec-23
 */
public enum OAuth2Provider {
	GOOGLE(1, "Google");

	private final int id;
	private final String name;

	/**
	 * @param id
	 * @param name
	 */
	OAuth2Provider(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isGoogle() {
		return this == GOOGLE;
	}
}

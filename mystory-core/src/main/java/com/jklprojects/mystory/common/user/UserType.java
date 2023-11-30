/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * Represents a list of User Types.
 *
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-03-23
 * @version 2, 2018-06-23
 * @since 2.18.03.23
 */
public enum UserType {
	APP_INTERNAL(1, "Application User"), OAUTH2_EXTERNAL(2, "OAuth2 External User");

	private static final String SIMPLE_NAME = UserType.class.getSimpleName();

	private final int id;
	private final String userTypeName;

	/**
	 * @param id
	 * @param userTypeName
	 */
	UserType(int id, String userTypeName) {
		this.id = id;
		this.userTypeName = userTypeName;
	}

	/**
	 * Lookup and return the matching object
	 *
	 * @param id
	 * @return matching object
	 */
	public static UserType toEnum(int id) {
		return Arrays.stream(UserType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(SIMPLE_NAME, id, name());
	}

	public int getId() {
		return id;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public boolean isAppInternal() {
		return this == UserType.APP_INTERNAL;
	}

	public boolean isOAuth2External() {
		return this == UserType.OAUTH2_EXTERNAL;
	}
}

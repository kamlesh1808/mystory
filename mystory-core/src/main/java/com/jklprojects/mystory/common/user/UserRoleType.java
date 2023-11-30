/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * Represents a list of User Role Types.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-05-12
 */
public enum UserRoleType {
	APP_USER(1, "App User"), APP_ADMIN(2, "App Admin"), SYS_ADMIN(3, "System Admin"), SYS_USERCOMM(4,
			"System User Comm"), SYS_GUEST_USER(5, "System Guest User"), GOOGLE(6, "Google", UserType.OAUTH2_EXTERNAL);

	private static final String SIMPLE_NAME = UserRoleType.class.getSimpleName();

	private final int id;
	private final String userRoleTypeName;
	private final UserType userType;

	/**
	 * @param id
	 * @param userRoleTypeName
	 */
	UserRoleType(int id, String userRoleTypeName) {
		this.id = id;
		this.userRoleTypeName = userRoleTypeName;
		this.userType = UserType.APP_INTERNAL;
	}

	/**
	 * @param id
	 * @param userRoleTypeName
	 * @param userType
	 */
	UserRoleType(int id, String userRoleTypeName, UserType userType) {
		this.id = id;
		this.userRoleTypeName = userRoleTypeName;
		this.userType = userType;
	}

	/**
	 * Lookup and return the matching object
	 *
	 * @param id
	 * @return matching object
	 */
	public static UserRoleType toEnum(int id) {
		return Arrays.stream(UserRoleType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public static UserRoleType toEnum(String id) {
		return toEnum(Integer.parseInt(id));
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(SIMPLE_NAME, id, name());
	}

	public int getId() {
		return id;
	}

	public String getUserRoleTypeName() {
		return userRoleTypeName;
	}

	public boolean isAppAdmin() {
		return this == UserRoleType.APP_ADMIN;
	}

	public boolean isAppUser() {
		return this == UserRoleType.APP_USER;
	}

	public boolean isGoogle() {
		return this == UserRoleType.GOOGLE;
	}

	public boolean isSysAdmin() {
		return this == UserRoleType.SYS_ADMIN;
	}

	public boolean isSysGuestUser() {
		return this == UserRoleType.SYS_GUEST_USER;
	}

	public boolean isSysUserComm() {
		return this == UserRoleType.SYS_USERCOMM;
	}

	public UserType getUserType() {
		return userType;
	}
}

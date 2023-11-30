/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.blacklist;

import java.util.Arrays;

/**
 * Application Environment Name defines a set of environment names.
 *
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-06-23
 * @since 2.18.06.23
 */
public enum BlackListType {
	PAGE_WATCH_USER_UID(1, "Page Watch User UID"), PAGE_WATCH_IP_ADDRESS(2, "Page Watch IP Address"), SIGN_IN_USER_UID(
			3, "Sign In User UID"), SIGN_IN_IP_ADDRESS(4, "Sign In IP Address"), STORY_WATCH_USER_UID(5,
					"Story Watch User UID"), STORY_WATCH_IP_ADDRESS(6, "Story Watch IP Address"), USER_WATCH_USER_UID(7,
							"User Watch User UID"), USER_WATCH_IP_ADDRESS(8,
									"User Watch IP Address"), ADVERT_WATCH_USER_UID(9,
											"Advert Watch User UID"), ADVERT_WATCH_IP_ADDRESS(10,
													"Advert Watch IP Address");

	private final int id;
	private final String name;

	BlackListType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static BlackListType toEnum(int id) {
		return Arrays.stream(BlackListType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isAdvertWatchIPAddress() {
		return this == ADVERT_WATCH_IP_ADDRESS;
	}

	public boolean isAdvertWatchUserUID() {
		return this == ADVERT_WATCH_USER_UID;
	}

	public boolean isPageWatchIPAddress() {
		return this == PAGE_WATCH_IP_ADDRESS;
	}

	public boolean isPageWatchUserUID() {
		return this == PAGE_WATCH_USER_UID;
	}

	public boolean isSignIntWatchUserUID() {
		return this == SIGN_IN_USER_UID;
	}

	public boolean isSignInWatchIPAddress() {
		return this == SIGN_IN_IP_ADDRESS;
	}

	public boolean isStoryWatchUserUID() {
		return this == STORY_WATCH_USER_UID;
	}

	public boolean isStoryWatchIPAddress() {
		return this == STORY_WATCH_IP_ADDRESS;
	}
}

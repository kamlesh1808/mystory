/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import java.util.Arrays;

/**
 * Represents an identifiable User Communication Name which is sent to users.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-10-24
 * @version 2, 2018-06-23
 * @version 3, 2018-12-23
 */
public enum UserCommName {
	ACCOUNT_VERIFY(1, UserCommType.EMAIL), ACCOUNT_VERIFIED(2, UserCommType.EMAIL), ACCOUNT_RECOVERY(3,
			UserCommType.EMAIL), CONTACTUS_CREATED(4, UserCommType.EMAIL), CONTACTUS_REPLY(5,
					UserCommType.EMAIL), STORY_CREATED(6, UserCommType.EMAIL), STORY_REVIEW(7,
							UserCommType.EMAIL), STORY_REVIEWED(8, UserCommType.EMAIL), PASSWORD_CHANGED(9,
									UserCommType.EMAIL), VERIFY_EMAIL_UPDATE(10, UserCommType.EMAIL),;

	private final int id;
	private final UserCommType userCommType;

	UserCommName(int id, UserCommType userCommType) {
		this.id = id;
		this.userCommType = userCommType;
	}

	public int getId() {
		return id;
	}

	public UserCommType getUserCommType() {
		return userCommType;
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching object
	 */
	public static UserCommName toEnum(int id) {
		return Arrays.stream(UserCommName.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public boolean isUserCommTypeEMail() {
		return userCommType == UserCommType.EMAIL;
	}

	/**
	 * Return true if this is in given, false otherwise.
	 *
	 * @param userCommNames
	 * @return
	 */
	public boolean in(UserCommName... userCommNames) {
		return Arrays.stream(userCommNames).anyMatch(e -> e.getId() == id);
	}
}

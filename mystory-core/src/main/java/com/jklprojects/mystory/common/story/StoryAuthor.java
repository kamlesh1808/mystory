/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

import com.jklprojects.mystory.common.api.IAppDTO;

/**
 * StoryAuthor SqlResultSetMapping entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-04-23
 */
public final class StoryAuthor implements IAppDTO {
	private String userUID;
	private int status;
	private String firstName;
	private String lastName;
	private int userRoleType;
	private int storiesCount;

	public StoryAuthor() {
		super();
	}

	/**
	 * @param userUID
	 * @param status
	 * @param firstName
	 * @param lastName
	 * @param storiesCount
	 */
	public StoryAuthor(final String userUID, final int status, final String firstName, final String lastName,
			int userRoleType, final int storiesCount) {

		this();

		this.userUID = userUID;
		this.status = status;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRoleType = userRoleType;
		this.storiesCount = storiesCount;
	}

	public final String getUserUID() {
		return userUID;
	}

	public final int getStatus() {
		return status;
	}

	public final String getFirstName() {
		return firstName;
	}

	public final String getLastName() {
		return lastName;
	}

	public final int getStoriesCount() {
		return storiesCount;
	}

	public final String getAuthor() {
		return getFirstName() + " " + getLastName();
	}

	public final String getAuthorStoriesCount() {
		return getAuthor() + " " + getStoriesCount();
	}

	public final String getAuthorURL() {
		return (getFirstName() + "-" + getLastName()).toLowerCase();
	}

	public int getUserRoleType() {
		return userRoleType;
	}
}

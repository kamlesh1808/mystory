/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user.dto;

import com.jklprojects.mystory.common.api.IAppDTO;
import com.jklprojects.mystory.common.user.UserRoleType;
import java.time.OffsetDateTime;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * DTO for displaying User Profile information.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
public class UserProfileDTO implements IAppDTO {

	private static final long serialVersionUID = 333342511561900L;

	private static final XLogger logger = XLoggerFactory.getXLogger(UserProfileDTO.class);

	private String aboutMe;
	private String firstName;
	private String lastName;
	private String userName;
	private UserRoleType userRoleType;
	private long userId;
	private String userUID;
	private OffsetDateTime createdTimestamp;
	private OffsetDateTime lastSeen;
	private long storiesCount;
	private long profileViews;

	/** no-arg default constructor */
	public UserProfileDTO() {
		super();
	}

	/**
	 * @param userId
	 * @param userUID
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param userRoleType
	 * @param aboutMe
	 * @param createdTimestamp
	 * @param storiesCount
	 * @param lastSeen
	 * @param profileViews
	 */
	public UserProfileDTO(long userId, String userUID, String userName, String firstName, String lastName,
			UserRoleType userRoleType, String aboutMe, OffsetDateTime createdTimestamp, long storiesCount,
			OffsetDateTime lastSeen, long profileViews) {

		super();

		this.userId = userId;
		this.userRoleType = userRoleType;
		this.aboutMe = aboutMe;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.userUID = userUID;
		this.createdTimestamp = createdTimestamp;
		this.storiesCount = storiesCount;
		this.lastSeen = lastSeen;
		this.profileViews = profileViews;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public String getAuthor() {
		return getFirstName() + " " + getLastName();
	}

	public String getAuthorURL() {
		return (getFirstName() + "-" + getLastName()).toLowerCase();
	}

	public OffsetDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public OffsetDateTime getLastSeen() {
		return lastSeen;
	}

	public long getProfileViews() {
		return profileViews;
	}

	public long getStoriesCount() {
		return storiesCount;
	}

	public String getUserName() {
		return userName;
	}

	public UserRoleType getUserRoleType() {
		return userRoleType;
	}

	public long getUserId() {
		return userId;
	}

	public long getUserRoleTypeId() {
		return userRoleType.getId();
	}

	public String getUserUID() {
		return userUID;
	}

	@Override
	public String toString() {
		return "UserProfileBean{" + "userRoleType=" + userRoleType + ", userId=" + userId + ", userUID='" + userUID
				+ '\'' + ", createdTimestamp=" + createdTimestamp + "} " + super.toString();
	}
}

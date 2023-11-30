/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user.dto;

import com.jklprojects.mystory.common.api.IAppDTO;
import com.jklprojects.mystory.common.user.UserRoleType;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2, 2018-05-23
 */
public class SignedInUserBean implements IAppDTO {

	private static final XLogger logger = XLoggerFactory.getXLogger(SignedInUserBean.class);

	private static final long serialVersionUID = -4180622227079756608L;

	private long id;
	private String firstName;
	private String lastName;
	private String userName;
	private UserRoleType userRoleType;
	private String userUID;

	public SignedInUserBean() {
		super();
	}

	/**
	 * s
	 *
	 * @param id
	 * @param userName
	 * @param userRoleType
	 * @param userUID
	 * @param firstName
	 * @param lastName
	 */
	public SignedInUserBean(long id, String userName, UserRoleType userRoleType, String userUID, String firstName,
			String lastName) {

		this();

		this.id = id;
		this.userName = userName;
		this.userRoleType = userRoleType;
		this.userUID = userUID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstLastName() {
		return new StringBuilder().append(getFirstName()).append(" ").append(getLastName()).toString();
	}

	public String getFirstName() {
		return firstName;
	}

	public long getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public UserRoleType getUserRoleType() {
		return userRoleType;
	}

	public String getUserUID() {
		return userUID;
	}

	@Override
	public String toString() {
		return "SignedInUserBean{" + "id=" + id + ", userRoleType=" + userRoleType + ", userUID='" + userUID + '\''
				+ '}';
	}
}

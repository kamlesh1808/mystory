/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.common.user.UserStatus;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents light weight view for User entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Apr 15, 2015
 */
@XmlRootElement
public class UserObject {
	private String firstName;
	private String lastName;
	private UserStatus userStatus;

	public UserObject() {
	}

	public String getFirstName() {
		return firstName;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}

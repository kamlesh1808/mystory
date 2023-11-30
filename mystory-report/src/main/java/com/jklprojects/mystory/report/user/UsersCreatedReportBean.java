/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.user;

import com.jklprojects.mystory.report.api.AbstractReportBean;
import java.util.Comparator;

/**
 * POJO Bean for use with Users Created report.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2017-11-23
 */
public class UsersCreatedReportBean extends AbstractReportBean implements Comparable<UsersCreatedReportBean> {

	private static final long serialVersionUID = -4667318922746655364L;

	private final String userName;
	private final String firstName;
	private final String lastName;
	private final String userUID;
	private final String userRoleType;
	private final String userStatus;

	/**
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param userUID
	 * @param userRoleType
	 * @param userStatus
	 * @param createdDateTime
	 * @param updatedDateTime
	 */
	public UsersCreatedReportBean(String userName, String firstName, String lastName, String userUID,
			String userRoleType, String userStatus, String createdDateTime, String updatedDateTime) {

		super(createdDateTime, updatedDateTime);

		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userStatus = userStatus;
		this.userRoleType = userRoleType;
		this.userUID = userUID;
	}

	@Override
	public int compareTo(UsersCreatedReportBean o) {
		return Comparator.comparing(UsersCreatedReportBean::getUserName)
				.thenComparing(UsersCreatedReportBean::getFirstName).thenComparing(UsersCreatedReportBean::getLastName)
				.compare(this, o);
	}

	@Override
	public String toString() {
		return "UsersCreatedReportBean{" + "userName='" + userName + '\'' + ", firstName='" + firstName + '\''
				+ ", lastName='" + lastName + '\'' + ", userUID='" + userUID + '\'' + ", userRoleType='" + userRoleType
				+ '\'' + ", userStatus='" + userStatus + '\'' + "} " + super.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UsersCreatedReportBean that = (UsersCreatedReportBean) o;

		return getUserUID() != null ? getUserUID().equals(that.getUserUID()) : that.getUserUID() == null;
	}

	@Override
	public int hashCode() {
		return getUserUID() != null ? getUserUID().hashCode() : 0;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserRoleType() {
		return userRoleType;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public String getUserUID() {
		return userUID;
	}
}

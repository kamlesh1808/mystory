/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.story;

import com.jklprojects.mystory.report.api.AbstractReportBean;
import java.util.Comparator;

/**
 * POJO Bean for use with Stories Views reports.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2, 2018-04-23
 */
public class StoriesViewsReportBean extends AbstractReportBean implements Comparable<StoriesViewsReportBean> {

	private static final long serialVersionUID = 6666505512089732704L;

	private String userUID;
	private final String firstName;
	private final String lastName;
	private String clientIPAddress;
	private String storyAccessType;
	private String storyName;
	private String storyStatus;
	private String storyTitle;
	private String storyType;

	/**
	 * @param userUID
	 * @param firstName
	 * @param lastName
	 * @param createdDateTime
	 * @param updatedDateTime
	 */
	public StoriesViewsReportBean(String userUID, String firstName, String lastName, String createdDateTime,
			String updatedDateTime) {
		super(createdDateTime, updatedDateTime);

		this.userUID = userUID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public int compareTo(StoriesViewsReportBean o) {
		return Comparator.comparing(StoriesViewsReportBean::getUserUID)
				.thenComparing(StoriesViewsReportBean::getFirstName).thenComparing(StoriesViewsReportBean::getLastName)
				.compare(this, o);
	}

	@Override
	public int hashCode() {
		int result = getUserUID() != null ? getUserUID().hashCode() : 0;
		result = 31 * result + (getStoryTitle() != null ? getStoryTitle().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		StoriesViewsReportBean that = (StoriesViewsReportBean) o;

		if (getUserUID() != null ? !getUserUID().equals(that.getUserUID()) : that.getUserUID() != null)
			return false;
		return getStoryTitle() != null ? getStoryTitle().equals(that.getStoryTitle()) : that.getStoryTitle() == null;
	}

	@Override
	public String toString() {
		return "StoriesViewsReportBean{" + "userUID='" + userUID + '\'' + ", firstName='" + firstName + '\''
				+ ", lastName='" + lastName + '\'' + ", clientIPAddress='" + clientIPAddress + '\''
				+ ", storyAccessType='" + storyAccessType + '\'' + ", storyName='" + storyName + '\''
				+ ", storyStatus='" + storyStatus + '\'' + ", storyTitle='" + storyTitle + '\'' + ", storyType='"
				+ storyType + '\'' + "} " + super.toString();
	}

	public String getClientIPAddress() {
		return clientIPAddress;
	}

	public void setClientIPAddress(String clientIPAddress) {
		this.clientIPAddress = clientIPAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getStoryAccessType() {
		return storyAccessType;
	}

	public void setStoryAccessType(String storyAccessType) {
		this.storyAccessType = storyAccessType;
	}

	public String getStoryName() {
		return storyName;
	}

	public void setStoryName(String storyName) {
		this.storyName = storyName;
	}

	public String getStoryStatus() {
		return storyStatus;
	}

	public void setStoryStatus(String storyStatus) {
		this.storyStatus = storyStatus;
	}

	public String getStoryTitle() {
		return storyTitle;
	}

	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}

	public String getStoryType() {
		return storyType;
	}

	public void setStoryType(String storyType) {
		this.storyType = storyType;
	}

	public String getUserUID() {
		return userUID;
	}

	public void setUserUID(String userUID) {
		this.userUID = userUID;
	}
}

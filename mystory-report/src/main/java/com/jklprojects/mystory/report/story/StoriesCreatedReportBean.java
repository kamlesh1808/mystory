/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.story;

import com.jklprojects.mystory.report.api.AbstractReportBean;
import java.util.Comparator;

/**
 * POJO Bean for use with Stories Created reports.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-11-23
 * @version 2, 2018-04-23
 */
public class StoriesCreatedReportBean extends AbstractReportBean implements Comparable<StoriesCreatedReportBean> {

	private static final long serialVersionUID = -2637845715986470590L;

	private String userName;
	private final String firstName;
	private final String lastName;
	private String storyAccessType;
	private String storyName;
	private String storyStatus;
	private String storyTitle;
	private String storyType;
	private int storyViews;

	/**
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param createdDateTime
	 * @param updatedDateTime
	 */
	public StoriesCreatedReportBean(String userName, String firstName, String lastName, String createdDateTime,
			String updatedDateTime) {
		super(createdDateTime, updatedDateTime);

		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public int compareTo(StoriesCreatedReportBean o) {
		return Comparator.comparing(StoriesCreatedReportBean::getUserName)
				.thenComparing(StoriesCreatedReportBean::getFirstName)
				.thenComparing(StoriesCreatedReportBean::getLastName).compare(this, o);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof StoriesCreatedReportBean))
			return false;

		StoriesCreatedReportBean that = (StoriesCreatedReportBean) o;

		if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
			return false;
		if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
			return false;
		if (getStoryName() != null ? !getStoryName().equals(that.getStoryName()) : that.getStoryName() != null)
			return false;
		if (getStoryTitle() != null ? !getStoryTitle().equals(that.getStoryTitle()) : that.getStoryTitle() != null)
			return false;
		return getStoryType() != null ? getStoryType().equals(that.getStoryType()) : that.getStoryType() == null;
	}

	@Override
	public int hashCode() {
		int result = getFirstName() != null ? getFirstName().hashCode() : 0;
		result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
		result = 31 * result + (getStoryName() != null ? getStoryName().hashCode() : 0);
		result = 31 * result + (getStoryTitle() != null ? getStoryTitle().hashCode() : 0);
		result = 31 * result + (getStoryType() != null ? getStoryType().hashCode() : 0);
		return result;
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

	public String getStoryName() {
		return storyName;
	}

	public String getStoryStatus() {
		return storyStatus;
	}

	public String getStoryTitle() {
		return storyTitle;
	}

	public String getStoryType() {
		return storyType;
	}

	public int getStoryViews() {
		return storyViews;
	}

	public String getUserName() {
		return userName;
	}

	public void setStoryAccessType(String storyAccessType) {
		this.storyAccessType = storyAccessType;
	}

	public void setStoryName(String storyName) {
		this.storyName = storyName;
	}

	public void setStoryStatus(String storyStatus) {
		this.storyStatus = storyStatus;
	}

	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}

	public void setStoryType(String storyType) {
		this.storyType = storyType;
	}

	public void setStoryViews(int storyViews) {
		this.storyViews = storyViews;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "StoriesCreatedReportBean{" + "userName='" + userName + '\'' + ", firstName='" + firstName + '\''
				+ ", lastName='" + lastName + '\'' + ", storyAccessType='" + storyAccessType + '\'' + ", storyName='"
				+ storyName + '\'' + ", storyStatus='" + storyStatus + '\'' + ", storyTitle='" + storyTitle + '\''
				+ ", storyType='" + storyType + '\'' + ", storyViews=" + storyViews + "} " + super.toString();
	}
}

/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.story;

import com.jklprojects.mystory.report.api.AppReportBean;

/**
 * POJO Bean for use with Story reports.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-SEP-21
 * @version 1, 2016-DEC-22
 */
public class StoryReportBean implements AppReportBean {
	private static final long serialVersionUID = 2108988460621710297L;

	private String userName;
	private String storyTitle;
	private String storyName;
	private String storyType;
	private String storyStatus;
	private String storyAccessType;
	private String storyPendingReviewMessage;

	public StoryReportBean() {
	}

	public String getStoryAccessType() {
		return storyAccessType;
	}

	public String getStoryName() {
		return storyName;
	}

	public final String getStoryPendingReviewMessage() {
		return storyPendingReviewMessage;
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

	public String getUserName() {
		return userName;
	}

	public void setStoryAccessType(String storyAccessType) {
		this.storyAccessType = storyAccessType;
	}

	public void setStoryName(String storyName) {
		this.storyName = storyName;
	}

	public final void setStoryPendingReviewMessage(String storyPendingReviewMessage) {
		this.storyPendingReviewMessage = storyPendingReviewMessage;
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

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "StoryReportBean [getUserName()=" + getUserName() + ", getStoryTitle()=" + getStoryTitle()
				+ ", getStoryName()=" + getStoryName() + ", getStoryType()=" + getStoryType() + ", getStoryStatus()="
				+ getStoryStatus() + ", getStoryAccessType()=" + getStoryAccessType() + "]";
	}
}

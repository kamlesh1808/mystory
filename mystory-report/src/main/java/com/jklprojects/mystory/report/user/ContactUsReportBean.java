/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.user;

import com.jklprojects.mystory.report.api.AppReportBean;

/**
 * POJO Bean for use with Contact Us reports.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-SEP-21
 */
public class ContactUsReportBean implements AppReportBean {

	private static final long serialVersionUID = -3742396462805328455L;

	private String userName;
	private String email;
	private String firstName;
	private String lastName;
	private String contactUsId;
	private String replyText;

	public ContactUsReportBean() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
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

	public String getContactUsId() {
		return contactUsId;
	}

	public void setContactUsId(String contactUsId) {
		this.contactUsId = contactUsId;
	}

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	@Override
	public String toString() {
		return "ContactUsReportBean [getUserName()=" + getUserName() + ", getEmail()=" + getEmail()
				+ ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getContactUsId()="
				+ getContactUsId() + ", getReplyText()=" + getReplyText() + "]";
	}
}

/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.report.user;

import com.jklprojects.mystory.report.api.AppReportBean;

/**
 * POJO Bean for use with Account reports.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-09-14
 * @version 1, 2017-01-23
 * @version 2, 2017-11-23
 * @version 3, 2018-12-23
 */
public class AccountReportBean implements AppReportBean {

	private static final long serialVersionUID = 5284062561827889871L;

	private String userName;
	private String email;
	private String emailOld;

	private String securityToken;
	private String tempPassword;

	public AccountReportBean() {
	}

	public String getEmail() {
		return email;
	}

	public String getEmailOld() {
		return emailOld;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailOld(String emailOld) {
		this.emailOld = emailOld;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountReportBean [getEmail()=").append(getEmail()).append(", getEmailOld()=")
				.append(getEmailOld()).append(", getSecurityToken()=").append(getSecurityToken())
				.append(", getTempPassword()=").append(getTempPassword()).append(", getUserName()=")
				.append(getUserName()).append("]");
		return builder.toString();
	}
}

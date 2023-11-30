/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.email.api;

import com.jklprojects.mystory.common.AppEnvName;
import java.io.Serializable;
import java.util.Properties;

/**
 * Immutable EMailerBean contains properties for sending email.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-04-26
 * @version 1, 2016-12-16
 * @version 1, 2017-05-23
 * @version 2, 2018-04-23
 */
public final class EMailerBean implements Serializable {

	private static final long serialVersionUID = 5853098570649701575L;

	private final String from;
	private final String encryptedPwd;
	private boolean mailAppDebug;
	private final boolean mailAppEnable;
	private final String mailAppTo;
	private final String mailAppAdminTo;
	private final String mailAppReplyTo;
	private final String mailAppSendinblueAPIKey;

	private final Properties smtpProps;

	private final AppEnvName appEnvName;

	/**
	 * @param appEnvName
	 * @param from
	 * @param encryptedPwd
	 * @param mailAppTo
	 * @param mailAppEnable
	 * @param mailAppAdminTo
	 * @param mailAppReplyTo
	 * @param mailAppSendinblueAPIKey
	 */
	public EMailerBean(AppEnvName appEnvName, final Properties smtpProps, final String from, final String encryptedPwd,
			final String mailAppTo, boolean mailAppEnable, String mailAppAdminTo, String mailAppReplyTo,
			String mailAppSendinblueAPIKey) {

		super();

		this.appEnvName = appEnvName;
		this.smtpProps = smtpProps;

		this.from = from;
		this.encryptedPwd = encryptedPwd;
		this.mailAppEnable = mailAppEnable;
		this.mailAppTo = mailAppTo;
		this.mailAppAdminTo = mailAppAdminTo;
		this.mailAppReplyTo = mailAppReplyTo;
		this.mailAppSendinblueAPIKey = mailAppSendinblueAPIKey;
	}

	/** @return the appEnvName */
	public AppEnvName getAppEnvName() {
		return appEnvName;
	}

	/**
	 * Get email to with respect to the application environment. If
	 * {@link AppEnvName#PROD return the given email to, otherwise return mail app
	 * debuug to.
	 *
	 * @param emailTo
	 * @return
	 *
	 * @see #getMailAppDebugTo
	 */
	public final String getEMailToWithAppEnvName(String emailTo) {
		return getAppEnvName().isPROD() ? emailTo : getMailTo();
	}

	public final String getEncryptedPwd() {
		return encryptedPwd;
	}

	public final String getFrom() {
		return from;
	}

	public final String getMailAppAdminTo() {
		return mailAppAdminTo;
	}

	public final String getMailTo() {
		return mailAppTo;
	}

	public String getMailAppReplyTo() {
		return mailAppReplyTo;
	}

	public String getMailAppSendinblueAPIKey() {
		return mailAppSendinblueAPIKey;
	}

	public final Properties getSMTPProps() {
		return smtpProps;
	}

	public final boolean isMailAppDebug() {
		return mailAppDebug;
	}

	public final boolean isMailAppEnable() {
		return mailAppEnable;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "EMailerBean{" + "from='" + from + '\'' + ", encryptedPwd='" + encryptedPwd + '\'' + ", mailAppDebug="
				+ mailAppDebug + ", mailAppEnable=" + mailAppEnable + ", mailAppTo='" + mailAppTo + '\''
				+ ", mailAppAdminTo='" + mailAppAdminTo + '\'' + ", mailAppReplyTo='" + mailAppReplyTo + '\''
				+ ", mailAppSendinblueAPIKey='" + mailAppSendinblueAPIKey + '\'' + ", smtpProps=" + smtpProps
				+ ", appEnvName=" + appEnvName + '}';
	}
}

/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.email.ejb;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;

import com.jklprojects.mystory.business.common.AppConfigConsts;
import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.business.common.util.EncryptDecryptor;
import com.jklprojects.mystory.business.email.EMailer;
import com.jklprojects.mystory.common.ex.AppException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Provides E-Mail services.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Apr 18, 2015
 * @version 2, Apr 26, 2016
 * @version 3, 2016-DEC-16
 * @version 3, 2017-Oct-23
 * @version 4, 2020-11-23
 */
@Stateless
public class EMailerImpl implements EMailerService, AppConfigConsts {

	private static final XLogger logger = XLoggerFactory.getXLogger(EMailerImpl.class);

	private EMailer eMailer;
	private String from = "";

	@EJB
	private AppConfigService appConfigService;

	public EMailerImpl() {
	}

	@PostConstruct
	public void _init() {
		logger.entry();

		Properties smtpProps = new Properties();
		smtpProps.put(MAIL_SMTP_HOST, appConfigService.getProperty(MAIL_SMTP_HOST));
		smtpProps.put(MAIL_SMTP_PORT, appConfigService.getProperty(MAIL_SMTP_PORT));
		smtpProps.put(MAIL_SMTP_AUTH, appConfigService.getProperty(MAIL_SMTP_AUTH));
		smtpProps.put(MAIL_SMTP_STARTTLS_ENABLE, appConfigService.getProperty(MAIL_SMTP_STARTTLS_ENABLE));
		smtpProps.put(MAIL_SMTP_SSL_TRUST, appConfigService.getProperty(MAIL_SMTP_SSL_TRUST));

		String userName = "";
		String encryptedPwd = "";
		String mailAppSendinblueAPIKey = "";
		String decryptedPwd = "";

		if (appConfigService.getAppEnvName().isDEV()) {
			from = appConfigService.getProperty(MAIL_APP_ADMIN_FROM_DEV);
			userName = appConfigService.getProperty(MAIL_APP_SENDINBLUE_USERNAME_DEV);
			encryptedPwd = appConfigService.getProperty(MAIL_APP_ADMIN_PWD_DEV);
			decryptedPwd = EncryptDecryptor.getInstance().decrypt(encryptedPwd);
			mailAppSendinblueAPIKey = appConfigService.getProperty(MAIL_APP_SENDINBLUE_API_KEY_DEV);
		} else if (appConfigService.getAppEnvName().isPROD()) {
			from = appConfigService.getProperty(MAIL_APP_ADMIN_FROM_PROD);
			userName = appConfigService.getProperty(MAIL_APP_SENDINBLUE_USERNAME_PROD);
			encryptedPwd = appConfigService.getProperty(MAIL_APP_ADMIN_PWD_PROD);
			decryptedPwd = EncryptDecryptor.getInstance().decrypt(encryptedPwd);
			mailAppSendinblueAPIKey = appConfigService.getProperty(MAIL_APP_SENDINBLUE_API_KEY_PROD);
		}

		Map<String, String> httpHeaders = Map.of(appConfigService.getProperty(MAIL_APP_SENDINBLUE_HEADERNAME_APIKEY),
				mailAppSendinblueAPIKey);

		eMailer = new EMailer(userName, decryptedPwd, smtpProps, httpHeaders);

		logger.info("EMailer created");

	}

	/**
	 * Send email with given parameters.
	 *
	 * @param to
	 * @param subject
	 * @param emailContent
	 * @param attachments
	 * @throws AppException
	 *             if an error is encountered sending email
	 */
	@Override
	public void sendEmail(List<String> to, String subject, String emailContent, List<File> attachments)
			throws AppException {

		Message message = eMailer.prepare(from, to, subject, emailContent, attachments);
		eMailer.sendEMailASyncESResult(message);
	}

}

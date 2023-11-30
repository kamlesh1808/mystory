/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.email;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jklprojects.mystory.business.common.AppConfigConsts;
import com.jklprojects.mystory.business.common.util.EncryptDecryptor;
import com.jklprojects.mystory.business.email.api.EMailStatus;
import com.jklprojects.mystory.common.ex.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-04-26
 * @version 1, 2017-01-23
 * @version 1, 2017-05-23
 * @version 2, 2018-04-23
 */
class EMailerTest implements AppConfigConsts {

	public static final String TEST_APP_CONFIG_NAME = "test-app-config.properties";

	private EMailer eMailer;
	private String from;

	@BeforeEach
	void setUpBefore() throws Exception {
		InputStream appConfigIS = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(TEST_APP_CONFIG_NAME);
		Properties appConfigProps = new Properties();
		appConfigProps.load(appConfigIS);

		Properties smtpProps = new Properties();
		smtpProps.put(MAIL_SMTP_HOST, appConfigProps.getProperty(MAIL_SMTP_HOST));
		smtpProps.put(MAIL_SMTP_PORT, appConfigProps.getProperty(MAIL_SMTP_PORT));
		smtpProps.put(MAIL_SMTP_AUTH, appConfigProps.getProperty(MAIL_SMTP_AUTH));
		smtpProps.put(MAIL_SMTP_STARTTLS_ENABLE, appConfigProps.getProperty(MAIL_SMTP_STARTTLS_ENABLE));
		smtpProps.put(MAIL_SMTP_SSL_TRUST, appConfigProps.getProperty(MAIL_SMTP_SSL_TRUST));

		from = appConfigProps.getProperty(MAIL_APP_ADMIN_FROM_DEV);
		String userName = appConfigProps.getProperty(MAIL_APP_SENDINBLUE_USERNAME_DEV);
		final String encryptedPwd = appConfigProps.getProperty(MAIL_APP_ADMIN_PWD_DEV);
		final String decryptedPwd = EncryptDecryptor.getInstance().decrypt(encryptedPwd);

		String mailAppSendinblueAPIKey = appConfigProps.getProperty(MAIL_APP_SENDINBLUE_API_KEY_DEV);
		Map<String, String> httpHeaders = Map.of(appConfigProps.getProperty(MAIL_APP_SENDINBLUE_HEADERNAME_APIKEY),
				mailAppSendinblueAPIKey);

		eMailer = new EMailer(userName, decryptedPwd, smtpProps, httpHeaders);
	}

	@Test
	@Disabled
	void test_sendEMailASyncESResult() throws AppException {
		Message message = eMailer.prepare(from, Arrays.asList(from), "subjectTest " + LocalDateTime.now().toString(),
				"emailContentTest " + LocalDateTime.now().toString(), null);
		EMailStatus eMailStatus = eMailer.sendEMailASyncESResult(message);
		assertTrue(EMailStatus.SENT == eMailStatus);
	}

}

/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.ejb;

import com.jklprojects.mystory.business.common.AppConfigConsts;
import com.jklprojects.mystory.business.common.api.AbstractAppPropertiesConfig;
import com.jklprojects.mystory.business.email.api.EMailerBean;
import com.jklprojects.mystory.common.AppEnvName;
import com.jklprojects.mystory.common.ex.AppRuntimeException;
import com.jklprojects.mystory.common.oauth2.OAuth2PropertiesBean;
import com.jklprojects.mystory.common.util.NumUtil;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * An application wide singleton bean to access application configuration
 * properties.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-12
 * @version 1, 2016-12-17
 * @version 1, 2017-11-23
 * @version 2, 2018-04-23
 * @version 2, 2018-05-23
 * @version 3, 2018-11-23
 * @version 4, 2019-06-23
 */
@Singleton(name = "appConfigBean")
@Startup
@AccessTimeout(value = 500, unit = TimeUnit.MILLISECONDS)
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Lock(LockType.READ)
public class AppConfigBean extends AbstractAppPropertiesConfig implements AppConfigService {

	private static final XLogger logger = XLoggerFactory.getXLogger(AppConfigBean.class);

	private EMailerBean eMailerBean;

	private OAuth2PropertiesBean oAuth2PropertiesBean;

	/**
	*/
	public AppConfigBean() {
		super();

		_initEMailerBean();
		_initOAuth2PropertiesBean();
	}

	@Override
	public AppEnvName getAppEnvName() {
		return AppEnvName.toAppEnvName(getProperty(AppConfigConsts.APP_ENV_NAME));
	}

	@Override
	public String getAppName() {
		return getProperty(AppConfigConsts.APP_NAME);
	}

	@Override
	public EMailerBean getEMailerBean() {
		return eMailerBean;
	}

	/**
	 * @param upToDate
	 *            - true to get up-to-date properties, false otherwise.
	 * @return
	 * @throws AppRuntimeException
	 */
	@Override
	public EMailerBean getEMailerBean(boolean upToDate) {
		if (upToDate) {
			loadProperties();
			_initEMailerBean();
		}
		return eMailerBean;
	}

	/**
	 * @see AppConfigService#getOAuth2PropertiesBean(boolean)
	 * @return
	 */
	@Override
	public OAuth2PropertiesBean getOAuth2PropertiesBean() {
		return oAuth2PropertiesBean;
	}

	/**
	 * @param reloadProps
	 *            true to get up-to-date properties, false otherwise.
	 * @return
	 * @throws AppRuntimeException
	 */
	@Override
	public OAuth2PropertiesBean getOAuth2PropertiesBean(boolean reloadProps) {
		if (reloadProps) {
			loadProperties();
			_initOAuth2PropertiesBean();
		}
		return oAuth2PropertiesBean;
	}

	@Override
	public String getPropertiesFileName() {
		return APP_CONFIG_FILE_NAME;
	}

	@Override
	@Lock(LockType.READ)
	public String getReportMyStoryIcon() {
		return getProperty(REPORT_MYSTORY_ICON);
	}

	@Override
	@Lock(LockType.READ)
	public String getReportMyStoryLogo() {
		return getProperty(REPORT_MYSTORY_LOGO);
	}

	@Override
	@Lock(LockType.READ)
	public String getRESTAPIAuthorizedHeaderName() {
		return getProperty(REST_API_AUTHORIZED_HEADER_NAME);
	}

	@Override
	@Lock(LockType.READ)
	public String getRESTAPIAuthorizedKey() {
		String key = "";
		if (getAppEnvName().isPROD()) {
			key = getProperty(REST_API_AUTHORIZED_KEY_PROD);
		} else if (getAppEnvName().isDEV()) {
			key = getProperty(REST_API_AUTHORIZED_KEY_DEV);
		}
		return key;
	}

	@Override
	@Lock(LockType.READ)
	public String getRESTAPIBaseURI() {
		String restAPIBaseURI = "";
		if (getAppEnvName().isPROD()) {
			restAPIBaseURI = getProperty(REST_API_BASE_URI_PROD);
		} else if (getAppEnvName().isDEV()) {
			restAPIBaseURI = getProperty(REST_API_BASE_URI_DEV);
		}
		return restAPIBaseURI;
	}

	@Override
	public String getScheduleAppReloadProps() {
		return getProperty(SCHEDULE_APP_RELOAD_PROPS);
	}

	@Override
	public String getScheduleAppSendUserComms() {
		return getProperty(SCHEDULE_APP_SEND_USER_COMMS);
	}

	@Override
	@Lock(LockType.READ)
	public String getScheduleEpochToLastWeekReport() {
		return getProperty(SCHEDULE_REPORT_EPOCH_TO_LAST_WEEK);
	}

	@Override
	public String getScheduleLastWeekReport() {
		return getProperty(SCHEDULE_REPORT_LAST_WEEK);
	}

	@Override
	public String getScheduleYesterdayReport() {
		return getProperty(SCHEDULE_REPORT_YESTERDAY);
	}

	@Override
	@Lock(LockType.READ)
	public int getSessionTimeOutInMinutes() {
		return NumUtil.parseInteger(getProperties().getProperty(SESSION_TIMEOUT_IN_MINUTES, "91"), 92);
	}

	@Override
	@Lock(LockType.READ)
	public int getStoryGeneralPostingEditExpiryInHours() {
		return NumUtil.parseInteger(getProperties().getProperty(STORY_GENERAL_POST_EDIT_EXPIRY_IN_HRS), 48);
	}

	@Override
	@Lock(LockType.READ)
	public int getStoryListSize() {
		return NumUtil.parseInteger(getProperties().getProperty(STORY_STORY_LIST_SIZE), 3);
	}

	@Override
	@Lock(LockType.READ)
	public int getStoryNumberOfBookAdvertsToDisplay() {
		return NumUtil.parseInteger(getProperties().getProperty(STORY_NUMBER_OF_BOOK_ADVERTS_TO_DISPLAY), 1);
	}

	@Override
	@Lock(LockType.READ)
	public int getStoryNumberOfProductAdvertsToDisplay() {
		return NumUtil.parseInteger(getProperties().getProperty(STORY_NUMBER_OF_PRODUCT_ADVERTS_TO_DISPLAY), 1);
	}

	@Override
	@Lock(LockType.READ)
	public int getStoryRelatedStoryListSize() {
		return NumUtil.parseInteger(getProperties().getProperty(STORY_RELATED_STORY_LIST_SIZE), 3);
	}

	@Override
	@Lock(LockType.READ)
	public int getStoryReplyEditExpiryInMinutes() {
		return NumUtil.parseInteger(getProperties().getProperty(STORY_STORYREPLY_EDIT_EXPIRY_IN_MINUTES), 10);
	}

	@Override
	@Lock(LockType.READ)
	public int getWriteQAccessExpireInSeconds() {
		return NumUtil.parseInteger(getProperties().getProperty(CACHE_WRITE_ACESS_EXPIRE_IN_SECONDS));
	}

	@Override
	@Lock(LockType.READ)
	public boolean isAppAuditEnabled() {
		return Boolean.parseBoolean(getProperties().getProperty(APP_AUDIT_ENABLED));
	}

	@Override
	@Lock(LockType.READ)
	public boolean isAppQuartzJobsEnabled() {
		return Boolean.parseBoolean(getProperties().getProperty(APP_QUARTZ_JOBS_ENABLED));
	}

	@Override
	@Lock(LockType.READ)
	public boolean isStoryMyStoryCommentsEnabled() {
		return Boolean.parseBoolean(getProperties().getProperty(STORY_MYSTORY_COMMENTS_ENABLE));
	}

	/**
	 * Reload application properties.
	 *
	 * @throws AppRuntimeException
	 */
	@Override
	@Schedule(second = "*/59", minute = "*", hour = "*", persistent = false)
	public void reloadProperties() throws IOException {
		long propsLastModifiedLatest = getPropertiesFileLastModified();
		long propsLastModifiedMilliSeconds = getPropsLastModifiedMilliSeconds();
		if (propsLastModifiedLatest > propsLastModifiedMilliSeconds) {
			logger.info(
					"Reload is required for properties file: {}, previous saved modified check: {}, last modified check: {}",
					getPropertiesFileName(), new Date(propsLastModifiedMilliSeconds).toString(),
					new Date(propsLastModifiedLatest).toString());
			loadProperties();
		} else {
			logger.debug(
					"Reload is NOT required for properties file: {}, previous saved modified check: {}, last modified check: {}",
					getPropertiesFileName(), new Date(propsLastModifiedMilliSeconds).toString(),
					new Date(propsLastModifiedLatest).toString());
		}
	}

	/*
	 * @PostConstruct public void scheduleAppQuartzJobs() { if
	 * (isAppQuartzJobsEnabled()) { try { new
	 * SchedulerQ(this).scheduleAppQuartzJobs(); } catch (SchedulerException e) {
	 * logger.catching(e); } } }
	 */

	private void _initEMailerBean() {
		logger.entry();

		Properties smtpProps = new Properties();
		smtpProps.put(MAIL_SMTP_HOST, getProperty(MAIL_SMTP_HOST));
		smtpProps.put(MAIL_SMTP_PORT, getProperty(MAIL_SMTP_PORT));
		smtpProps.put(MAIL_SMTP_AUTH, getProperty(MAIL_SMTP_AUTH));
		smtpProps.put(MAIL_SMTP_STARTTLS_ENABLE, getProperty(MAIL_SMTP_STARTTLS_ENABLE));
		smtpProps.put(MAIL_SMTP_SSL_TRUST, getProperty(MAIL_SMTP_SSL_TRUST));

		String from = "";
		String encryptedPwd = "";
		String mailAppAdminTo = "";
		String mailAppSendInBlueAPIKey = "";

		boolean mailAppEnable = getPropertyBoolean(MAIL_APP_ENABLE);
		String mailAppTo = "";
		String mailAppReplyTo = getProperty(MAIL_APP_REPLY_TO);

		AppEnvName appEnvName = getAppEnvName();

		if (appEnvName.isPROD()) {
			from = getProperty(MAIL_APP_ADMIN_FROM_PROD);
			encryptedPwd = getProperty(MAIL_APP_ADMIN_PWD_PROD);
			mailAppAdminTo = getProperty(MAIL_APP_ADMIN_TO_PROD);
			mailAppSendInBlueAPIKey = getProperty(MAIL_APP_SENDINBLUE_API_KEY_PROD);
		} else if (appEnvName.isDEV()) {
			mailAppTo = getProperty(MAIL_APP_TO_DEV);

			from = getProperty(MAIL_APP_ADMIN_FROM_DEV);
			encryptedPwd = getProperty(MAIL_APP_ADMIN_PWD_DEV);
			mailAppAdminTo = getProperty(MAIL_APP_ADMIN_TO_DEV);
			mailAppSendInBlueAPIKey = getProperty(MAIL_APP_SENDINBLUE_API_KEY_DEV);
		}

		eMailerBean = new EMailerBean(appEnvName, smtpProps, from, encryptedPwd, mailAppTo, mailAppEnable,
				mailAppAdminTo, mailAppReplyTo, mailAppSendInBlueAPIKey);

		logger.exit(eMailerBean.toString());
	}

	/**
	 * @return
	 * @throws AppRuntimeException
	 */
	private void _initOAuth2PropertiesBean() {
		String clientId = "";
		String clientSecret = "";
		String callbackURI = "";

		AppEnvName appEnvName = getAppEnvName();

		if (appEnvName.isPROD()) {
			clientId = getProperty(AppConfigConsts.OAUTH2_GOOGLE_CLIENT_ID_PROD);
			clientSecret = getProperty(AppConfigConsts.OAUTH2_GOOGLE_CLIENT_SECRET_PROD);
			callbackURI = getProperty(AppConfigConsts.OAUTH2_GOOGLE_CALLBACK_URI_PROD);
		} else if (appEnvName.isDEV()) {
			clientId = getProperty(AppConfigConsts.OAUTH2_GOOGLE_CLIENT_ID_DEV);
			clientSecret = getProperty(AppConfigConsts.OAUTH2_GOOGLE_CLIENT_SECRET_DEV);
			callbackURI = getProperty(AppConfigConsts.OAUTH2_GOOGLE_CALLBACK_URI_DEV);
		}
		oAuth2PropertiesBean = new OAuth2PropertiesBean(callbackURI, clientId, clientSecret);
	}
}

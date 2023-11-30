/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common;

import com.jklprojects.mystory.common.AppEnvName;

/**
 * App Config property keys constants.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-09-02
 * @version 1, 2016-12-16
 * @version 1, 2017-10-23
 * @version 1, 2017-11-23
 * @version 2, 2018-04-23
 * @version 2, 2018-05-23
 * @version 3, 2018-10-23
 * @version 3, 2018-11-23
 */
public interface AppConfigConsts {

	// application
	String APP_AUDIT_ENABLED = "appAuditEnabled";
	String APP_CONFIG_FILE_NAME = "app-config.properties";
	String APP_ENV_NAME = "appEnvName";
	String APP_NAME = "appName";
	String APP_QUARTZ_JOBS_ENABLED = "appQuartzJobsEnabled";
	String APP_RELEASE_NAME = "appReleaseName";
	String APP_VERSION = "appVersion";
	String APP_DISQUS_SHORTNAME = "appDisqusShortName";
	String APP_DISQUS_SHORTNAME_PROD = "appDisqusShortName" + "." + AppEnvName.PROD.getName();

	// advert
	String ADVERT_GOOGLE_ADSENSE_ENABLED = "advertGoogleAdSenseEnabled";

	String MAIL_APP_ADMIN_FROM = "mail.app.admin.from";
	String MAIL_APP_ADMIN_FROM_DEV = MAIL_APP_ADMIN_FROM + "." + AppEnvName.DEV.getName();
	String MAIL_APP_ADMIN_FROM_PROD = MAIL_APP_ADMIN_FROM + "." + AppEnvName.PROD.getName();
	String MAIL_APP_ADMIN_PWD = "mail.app.admin.pwd";
	String MAIL_APP_ADMIN_PWD_DEV = MAIL_APP_ADMIN_PWD + "." + AppEnvName.DEV.getName();
	String MAIL_APP_ADMIN_PWD_PROD = MAIL_APP_ADMIN_PWD + "." + AppEnvName.PROD.getName();
	String MAIL_APP_ADMIN_TO = "mail.app.admin.to";
	String MAIL_APP_ADMIN_TO_DEV = MAIL_APP_ADMIN_TO + "." + AppEnvName.DEV.getName();
	String MAIL_APP_ADMIN_TO_PROD = MAIL_APP_ADMIN_TO + "." + AppEnvName.PROD.getName();

	String MAIL_APP_SENDINBLUE_USERNAME = "mail.app.sendinblue.username";
	String MAIL_APP_SENDINBLUE_USERNAME_DEV = MAIL_APP_SENDINBLUE_USERNAME + "." + AppEnvName.DEV.getName();
	String MAIL_APP_SENDINBLUE_USERNAME_PROD = MAIL_APP_SENDINBLUE_USERNAME + "." + AppEnvName.PROD.getName();
	String MAIL_APP_SENDINBLUE_HEADERNAME_APIKEY = "mail.app.sendinblue.headername.apikey";

	/** true enables application sending of email, false disables it */
	String MAIL_APP_ENABLE = "mail.app.enable";

	String MAIL_APP_REPLY_TO = "mail.app.replyTo";

	String MAIL_APP_SENDINBLUE_API_KEY = "mail.app.sendinblueAPIKey";
	String MAIL_APP_SENDINBLUE_API_KEY_DEV = MAIL_APP_SENDINBLUE_API_KEY + "." + AppEnvName.DEV.getName();
	String MAIL_APP_SENDINBLUE_API_KEY_PROD = MAIL_APP_SENDINBLUE_API_KEY + "." + AppEnvName.PROD.getName();
	String MAIL_APP_TO = "mail.app.to";
	String MAIL_APP_TO_DEV = "mail.app.to" + "." + AppEnvName.DEV.getName();

	// email
	String MAIL_SMTP_AUTH = "mail.smtp.auth";
	String MAIL_SMTP_HOST = "mail.smtp.host";
	String MAIL_SMTP_PORT = "mail.smtp.port";
	String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";

	// oauth2
	String OAUTH2_GOOGLE_CALLBACK_URI = "oAuth2.google.callbackURI";
	String OAUTH2_GOOGLE_CALLBACK_URI_DEV = OAUTH2_GOOGLE_CALLBACK_URI + "." + AppEnvName.DEV.getName();
	String OAUTH2_GOOGLE_CALLBACK_URI_PROD = OAUTH2_GOOGLE_CALLBACK_URI + "." + AppEnvName.PROD.getName();

	String OAUTH2_GOOGLE_CLIENT_ID = "oAuth2.google.clientId";
	String OAUTH2_GOOGLE_CLIENT_ID_DEV = OAUTH2_GOOGLE_CLIENT_ID + "." + AppEnvName.DEV.getName();
	String OAUTH2_GOOGLE_CLIENT_ID_PROD = OAUTH2_GOOGLE_CLIENT_ID + "." + AppEnvName.PROD.getName();

	String OAUTH2_GOOGLE_CLIENT_SECRET = "oAuth2.google.clientSecret";
	String OAUTH2_GOOGLE_CLIENT_SECRET_DEV = OAUTH2_GOOGLE_CLIENT_SECRET + "." + AppEnvName.DEV.getName();
	String OAUTH2_GOOGLE_CLIENT_SECRET_PROD = OAUTH2_GOOGLE_CLIENT_SECRET + "." + AppEnvName.PROD.getName();

	// report
	String REPORT_MYSTORY_ICON = "report.mystoryIcon";
	String REPORT_MYSTORY_LOGO = "report.mystoryLogo";

	// REST API
	String REST_API_BASE_URI = "restAPI.baseURI";
	String REST_API_BASE_URI_DEV = REST_API_BASE_URI + "." + AppEnvName.DEV.getName();
	String REST_API_BASE_URI_PROD = REST_API_BASE_URI + "." + AppEnvName.PROD.getName();

	String REST_API_AUTHORIZED_HEADER_NAME = "restAPI.authorizedHeaderName";
	String REST_API_AUTHORIZED_KEY = "restAPI.authorizedKey";
	String REST_API_AUTHORIZED_KEY_DEV = REST_API_AUTHORIZED_KEY + "." + AppEnvName.DEV.getName();
	String REST_API_AUTHORIZED_KEY_PROD = REST_API_AUTHORIZED_KEY + "." + AppEnvName.PROD.getName();

	// schedules - app
	String SCHEDULE_APP_RELOAD_PROPS = "schedule.app.reloadProps";
	String SCHEDULE_APP_SEND_USER_COMMS = "schedule.app.sendUserComms";

	// report schedules
	String SCHEDULE_REPORT_EPOCH_TO_LAST_WEEK = "schedule.report.epochToLastWeek";
	String SCHEDULE_REPORT_LAST_WEEK = "schedule.report.lastWeek";
	String SCHEDULE_REPORT_YESTERDAY = "schedule.report.yesterday";
	String SESSION_TIMEOUT_IN_MINUTES = "userSessionTimeOutInMinutes";

	// story
	String STORY_GENERAL_POST_EDIT_EXPIRY_IN_HRS = "story.generalPostingEditExpiryInHours";
	String STORY_MYSTORY_COMMENTS_ENABLE = "story.mystory.comments.enable";
	String STORY_RELATED_STORY_LIST_SIZE = "story.relatedstoryListSize";
	String STORY_STORY_LIST_SIZE = "story.storyListSize";
	String STORY_STORYREPLY_EDIT_EXPIRY_IN_MINUTES = "story.storyReplyEditExpiryInMinutes";

	String STORY_NUMBER_OF_BOOK_ADVERTS_TO_DISPLAY = "story.numberOfBookAdvertsToDisplay";
	String STORY_NUMBER_OF_PRODUCT_ADVERTS_TO_DISPLAY = "story.numberOfProductAdvertsToDisplay";

	// user
	String USER_ACCOUNT_RECOVERY_DURATION_IN_MINS = "userAccountRecoveryDurationInMins";
	String USER_MAX_INVALID_SIGNIN_ATTEMPTS = "userMaxInvalidSigninAttempts";

	// catche
	String CACHE_WRITE_ACESS_EXPIRE_IN_SECONDS = "cache.writeAccessExpireInSeconds";
}

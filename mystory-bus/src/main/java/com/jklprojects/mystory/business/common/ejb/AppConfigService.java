/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.ejb;

import com.jklprojects.mystory.business.common.AppConfigConsts;
import com.jklprojects.mystory.business.common.api.AppPropertiesConfig;
import com.jklprojects.mystory.business.email.api.EMailerBean;
import com.jklprojects.mystory.common.AppEnvName;
import com.jklprojects.mystory.common.oauth2.OAuth2PropertiesBean;
import javax.ejb.Local;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-12
 * @version 1, 2016-12-17
 * @version 1, 2017-10-23
 * @version 1, 2017-11-23
 * @version 2, 2017-12-23
 * @version 2, 2018-03-23
 * @version 2, 2018-04-23
 * @version 2, 2018-11-23
 * @version 3, 2018-10-23
 * @version 3, 2018-11-23
 * @version 4, 2019-06-23
 */
@Local
public interface AppConfigService extends AppPropertiesConfig, AppConfigConsts {
	String getAppName();

	AppEnvName getAppEnvName();

	EMailerBean getEMailerBean();

	EMailerBean getEMailerBean(boolean upToDate);

	OAuth2PropertiesBean getOAuth2PropertiesBean();

	OAuth2PropertiesBean getOAuth2PropertiesBean(boolean upToDate);

	String getReportMyStoryIcon();

	String getReportMyStoryLogo();

	String getRESTAPIAuthorizedHeaderName();

	String getRESTAPIAuthorizedKey();

	String getRESTAPIBaseURI();

	String getScheduleAppReloadProps();

	String getScheduleAppSendUserComms();

	String getScheduleEpochToLastWeekReport();

	String getScheduleLastWeekReport();

	String getScheduleYesterdayReport();

	int getSessionTimeOutInMinutes();

	int getStoryGeneralPostingEditExpiryInHours();

	int getStoryListSize();

	int getStoryNumberOfBookAdvertsToDisplay();

	int getStoryNumberOfProductAdvertsToDisplay();

	int getStoryRelatedStoryListSize();

	int getStoryReplyEditExpiryInMinutes();

	boolean isAppAuditEnabled();

	boolean isAppQuartzJobsEnabled();

	boolean isStoryMyStoryCommentsEnabled();

	int getWriteQAccessExpireInSeconds();
}

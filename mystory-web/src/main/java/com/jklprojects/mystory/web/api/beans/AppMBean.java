/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.api.beans;

import com.jklprojects.mystory.business.common.AppConfigConsts;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.common.AppEnvName;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.util.AppUtil;
import com.jklprojects.mystory.common.util.StringUtil;
import com.jklprojects.mystory.common.util.TimeUtil;
import com.jklprojects.mystory.web.AppURLRewriteConsts;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Application Managed Bean.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-05-29
 */
@Named("appMBean")
@ApplicationScoped
public class AppMBean extends AbstractAppManagedBean implements AppURLRewriteConsts {

	private static final long serialVersionUID = -2688759537124136760L;

	private static final XLogger logger = XLoggerFactory.getXLogger(AppMBean.class);

	private Manifest warManifest;

	private String appImplementationVersion = "";

	private String disqusShortName;
	private int storyListSize;
	private int storyRelatedStoryListSize;
	private int storyNumberOfBookAdvertsToDisplay;
	private int storyNumberOfProductAdvertsToDisplay;
	private boolean storyMyStoryCommentsEnabled;

	public AppMBean() {
		super();
	}

	@PostConstruct
	public void init() {
		logger.entry();

		storyListSize = getAppConfigService().getStoryListSize();
		storyRelatedStoryListSize = getAppConfigService().getStoryRelatedStoryListSize();
		storyMyStoryCommentsEnabled = getAppConfigService().isStoryMyStoryCommentsEnabled();
		storyNumberOfBookAdvertsToDisplay = getAppConfigService().getStoryNumberOfBookAdvertsToDisplay();
		storyNumberOfProductAdvertsToDisplay = getAppConfigService().getStoryNumberOfProductAdvertsToDisplay();
	}

	public int getStoryRelatedStoryListSize() {
		return storyRelatedStoryListSize;
	}

	public int getStoryListSize() {
		return storyListSize;
	}

	public int getStoryNumberOfBookAdvertsToDisplay() {
		return storyNumberOfBookAdvertsToDisplay;
	}

	public int getStoryNumberOfProductAdvertsToDisplay() {
		return storyNumberOfProductAdvertsToDisplay;
	}

	public boolean isStoryMyStoryCommentsEnabled() {
		return storyMyStoryCommentsEnabled;
	}

	/**
	 * Build and return time duration string. Return empty string if local date time
	 * is null.
	 *
	 * @param localDateTime
	 * @return
	 */
	public String buildTimeDuration(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return "";
		}

		final long years = TimeUtil.agoYears(localDateTime);
		final long months = TimeUtil.agoMonths(localDateTime);
		final long days = TimeUtil.agoDays(localDateTime);
		final long hours = TimeUtil.agoHours(localDateTime);
		final long minutes = TimeUtil.agoMinutes(localDateTime);

		if (years > 0) {
			if (years > 1) {
				return new StringBuilder().append(years).append(" ").append(I18N.APP.getI18N("common_years"))
						.toString();
			} else {
				return new StringBuilder(I18N.APP.getI18N("common_aYear")).toString();
			}
		} else if (months > 0) {
			if (months > 1) {
				return new StringBuilder().append(months).append(" ").append(I18N.APP.getI18N("common_months"))
						.toString();
			} else {
				return new StringBuilder(I18N.APP.getI18N("common_aMonth")).toString();
			}
		} else if (days > 0) {
			if (days > 1) {
				return new StringBuilder().append(days).append(" ").append(I18N.APP.getI18N("common_days")).toString();
			} else {
				return new StringBuilder(I18N.APP.getI18N("common_aDay")).toString();
			}
		} else if (hours > 0) {
			if (hours > 1) {
				return new StringBuilder().append(hours).append(" ").append(I18N.APP.getI18N("common_hours"))
						.toString();
			} else {
				return new StringBuilder(I18N.APP.getI18N("common_anHour")).toString();
			}
		} else if (minutes > 0) {
			if (minutes > 1) {
				return new StringBuilder().append(minutes).append(" ").append(I18N.APP.getI18N("common_minutes"))
						.toString();
			} else {
				return new StringBuilder(I18N.APP.getI18N("common_aMinute")).toString();
			}
		} else {
			return I18N.APP.getI18N("common_seconds");
		}
	}

	/**
	 * Build and return time duration string.
	 *
	 * @param offsetDateTime
	 * @return
	 */
	public String buildTimeDuration(OffsetDateTime offsetDateTime) {
		if (offsetDateTime != null) {
			return buildTimeDuration(offsetDateTime.toLocalDateTime());
		} else {
			return "";
		}
	}

	public boolean isAdvertGoogleAdsenseEnabled() {
		return getAppConfigService().getPropertyBoolean(AppConfigConsts.ADVERT_GOOGLE_ADSENSE_ENABLED);
	}

	public String getAppDisqusShortName() {
		if (StringUtil.isEmpty(disqusShortName)) {
			if (isAppEnvProd()) {
				disqusShortName = getAppConfigService().getProperty(AppConfigConsts.APP_DISQUS_SHORTNAME_PROD);
			} else {
				disqusShortName = getAppConfigService().getProperty(AppConfigConsts.APP_DISQUS_SHORTNAME);
			}
		}
		return disqusShortName;
	}

	public String getAppEnvName() {
		return getAppConfigService().getProperty(AppConfigConsts.APP_ENV_NAME);
	}

	public String getAppImplementationVersion() {
		try {
			if (StringUtil.isEmpty(appImplementationVersion)) {
				if (getAppWARManifest() != null) {
					Attributes mainAttribs = getAppWARManifest().getMainAttributes();
					appImplementationVersion = AppUtil.getManifestAttributeValue(getAppWARManifest(),
							Attributes.Name.IMPLEMENTATION_VERSION);
				}
			}
		} catch (Exception e) {
			logger.catching(e);
		}
		return appImplementationVersion;
	}

	public String getAppName() {
		return getAppConfigService().getProperty(AppConfigConsts.APP_NAME);
	}

	public String getAppReleaseName() {
		return getAppConfigService().getProperty(AppConfigConsts.APP_RELEASE_NAME);
	}

	public String getAppVersion() {
		return getAppConfigService().getProperty(AppConfigConsts.APP_VERSION);
	}

	/**
	 * Return the WAR's Manifest.
	 *
	 * @return
	 * @throws Exception
	 */
	public Manifest getAppWARManifest() throws Exception {
		if (warManifest == null) {
			warManifest = new Manifest(getAppServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
		}
		return warManifest;
	}

	public String getAgo(StoryView story) {
		return getTimeDurationWithAgo(story.getCreatedTimestamp());
	}

	public String getAgo(OffsetDateTime offsetDateTime) {
		return getTimeDurationWithAgo(offsetDateTime);
	}

	/**
	 * Construct and return time duration "ago" string.
	 *
	 * @param offsetDateTime
	 * @return
	 */
	public String getTimeDurationWithAgo(OffsetDateTime offsetDateTime) {
		if (offsetDateTime == null) {
			return "";
		}

		return new StringBuilder(buildTimeDuration(offsetDateTime.toLocalDateTime())).append(" ")
				.append(I18N.APP.getI18N("common_ago")).toString();
	}

	public String getUpdatedAgo(StoryView story) {
		if (story == null || story.getStoryTextUpdatedTimestamp() == null) {
			return "";
		}

		return new StringBuilder(buildTimeDuration(story.getStoryTextUpdatedTimestamp().toLocalDateTime())).append(" ")
				.append(I18N.APP.getI18N("common_ago")).toString();
	}

	/**
	 * @return true if the configured app environment name is
	 *         {@link AppEnvName#DEV}, false otherwise.
	 */
	public boolean isAppEnvDev() {
		return isAppEnvNameEqual(AppEnvName.DEV);
	}

	/**
	 * @return true if the configured app environment name is
	 *         {@link AppEnvName#PROD}, false otherwise.
	 */
	public boolean isAppEnvProd() {
		return isAppEnvNameEqual(AppEnvName.PROD);
	}

	/**
	 * @return true if the configured app environment name is {@link AppEnvName#QA},
	 *         false otherwise.
	 */
	public boolean isAppEnvQA() {
		return isAppEnvNameEqual(AppEnvName.QA);
	}

	/**
	 * @return true if the configured app environment name is
	 *         {@link AppEnvName#UAT}, false otherwise.
	 */
	public boolean isAppEnvUAT() {
		return isAppEnvNameEqual(AppEnvName.UAT);
	}

	private boolean isAppEnvNameEqual(AppEnvName appEnvName) {
		final AppEnvName configAppEnvName = AppEnvName.toAppEnvName(getAppEnvName());
		return configAppEnvName != null && appEnvName == configAppEnvName;
	}
}

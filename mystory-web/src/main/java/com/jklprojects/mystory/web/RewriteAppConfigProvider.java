/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web;

import com.jklprojects.mystory.web.auth.OAuth2CallbackServlet;
import javax.servlet.ServletContext;
import org.ocpsoft.logging.Logger.Level;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Log;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Responsible for URL Rewriting. URLs are based off REST API
 * {@link https://restfulapi.net/resource-naming/}
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @see OAuth2CallbackServlet
 * @since 1, 2016-12-10
 */
@RewriteConfiguration
public class RewriteAppConfigProvider extends HttpConfigurationProvider
		implements
			AppURLRewriteConsts,
			RequestParamNames {

	private static final XLogger logger = XLoggerFactory.getXLogger(RewriteAppConfigProvider.class);

	@Override
	public Configuration getConfiguration(ServletContext context) {
		final ConfigurationBuilder cb = ConfigurationBuilder.begin();

		cb.addRule().perform(Log.message(Level.DEBUG, "Rewrite is active."));
		logger.debug("Rewrite building configuration...");

		/*
		 * Note: forward slash at the end is required to show icons for Primefaces
		 * editor and extension ckeditor
		 */

		//
		// site pages
		//

		cb.addRule(Join.path("/").to(new StringBuilder(PUB).append(SITE).append(APP_HOME_PAGE).toString()));

		cb.addRule(Join.path(ADS_TXT_PAGE).to(new StringBuilder(PUB).append(SITE).append(ADS_TXT_PAGE).toString()));
		cb.addRule(
				Join.path(ROBOTS_TXT_PAGE).to(new StringBuilder(PUB).append(SITE).append(ROBOTS_TXT_PAGE).toString()));

		// using /signIn without the / at the end, for google signIn to work
		cb.addRule(Join.path(SIGN_IN).to(new StringBuilder(PUB).append(USER).append(SIGN_IN_PAGE).toString()));

		cb.addRule(Join.path(SIGN_UP_ROOT).to(new StringBuilder(PUB).append(USER).append(SIGN_UP_PAGE).toString()));

		cb.addRule(Join.path(ABOUT_US).to(new StringBuilder(PUB).append(SITE).append(ABOUT_US_PAGE).toString()));

		cb.addRule(Join.path(CONTACT_US).to(new StringBuilder(PUB).append(SITE).append(CONTACT_US_PAGE).toString()));
		cb.addRule(Join.path(CONTACT_US_REPLY)
				.to(new StringBuilder(SECURED_RESOURCE_URI_APP).append(USER).append(CONTACT_US_REPLY_PAGE).toString()));

		cb.addRule(Join.path(PRIVACY_POLICY)
				.to(new StringBuilder(PUB).append(SITE).append(PRIVACY_POLICY_PAGE).toString()));

		cb.addRule(Join.path(SECURITY_POLICY)
				.to(new StringBuilder(PUB).append(SITE).append(SECURITY_POLICY_PAGE).toString()));

		cb.addRule(Join.path(TERMS_OF_SERVICE)
				.to(new StringBuilder(PUB).append(SITE).append(TERMS_OF_SERVICE_PAGE).toString()));

		cb.addRule(Join.path(ACKNOWLEDGEMENTS)
				.to(new StringBuilder(PUB).append(SITE).append(ACKNOWLEDGEMENTS_PAGE).toString()));

		cb.addRule(Join.path(REST_APIS).to(new StringBuilder(PUB).append(SITE).append(REST_APIS_PAGE).toString()));
		cb.addRule(Join.path(SPONSORS).to(new StringBuilder(PUB).append(SITE).append(SPONSORS_PAGE).toString()));
		cb.addRule(Join.path(DONATE).to(new StringBuilder(PUB).append(SITE).append(DONATE_PAGE).toString()));
		cb.addRule(Join.path(SUPPORT_US).to(new StringBuilder(PUB).append(SITE).append(SUPPORT_US_PAGE).toString()));

		//
		// user pages
		//
		cb.addRule(Join.path(new StringBuilder(PASSWORD_RESET).toString())
				.to(new StringBuilder(PUB).append(USER).append(PASSWORD_RESET_PAGE).toString()));

		cb.addRule(Join.path(new StringBuilder(USER_ACCOUNT).toString()).to(
				new StringBuilder(SECURED_RESOURCE_URI_APP).append(USER).append(USER_ACCOUNT_EDIT_PAGE).toString()));

		cb.addRule(Join.path(new StringBuilder(USER_OAUTH2_ACCOUNT).toString())
				.to(new StringBuilder(SECURED_RESOURCE_URI_APP).append(USER_OAUTH2)
						.append(USER_OAUTH2_ACCOUNT_EDIT_PAGE).toString()));

		cb.addRule(Join.path(new StringBuilder(USERS).toString())
				.to(new StringBuilder(SECURED_RESOURCE_URI_APP).append(USER).append(USERS_PAGE).toString()));

		// user page
		cb.addRule(Join.path(new StringBuilder(USER).toString())
				.to(new StringBuilder(SECURED_RESOURCE_URI_APP).append(USER).append(USER_PAGE).toString()));

		cb.addRule(Join.path(USERS_ROOT + getParam(REQ_PARAM_USER_UID, "-") + getParam(REQ_PARAM_USER_ROLE_TYPE_ID)
				+ getParam(REQ_PARAM_AUTHOR_URL, "")).to(PUB + USER + USER_PROFILE_PAGE));

		cb.addRule(Join.path(new StringBuilder(CONTACT_US_REPLY).append(getParam(REQ_PARAM_CONTACTUS_ID)).toString())
				.to(new StringBuilder(SECURED_RESOURCE_URI_APP).append(USER).append(CONTACT_US_REPLY_PAGE).toString()));

		//
		// story pages
		cb.addRule(Join.path(STORY_SHARE)
				.to(new StringBuilder(SECURED_RESOURCE_URI_APP).append(STORY).append(STORY_ADD_PAGE).toString()));

		String storyParams = getParam(REQ_PARAM_STORY_ID) + getParam(REQ_PARAM_STORY_NAME)
				+ getParam(REQ_PARAM_STORY_TITLE, "");

		final String storyPath = STORIES_ROOT + storyParams;

		logger.debug("storyPath {}", storyPath);
		cb.addRule(Join.path(new StringBuilder(STORY_EDIT_ROOT).append(storyParams).toString())
				.to(new StringBuilder(SECURED_RESOURCE_URI_APP).append(STORY).append(STORY_EDIT_PAGE).toString()));

		cb.addRule(Join.path(storyPath).to(new StringBuilder(PUB).append(STORY).append(STORY_SHARED_PAGE).toString()));

		cb.addRule(Join.path(new StringBuilder(STORY_REVIEW_ROOT).append(storyParams).toString())
				.to(new StringBuilder(SECURED_RESOURCE_URI_APP).append(STORY).append(STORY_REVIEW_PAGE).toString()));

		final String tagStoriesPath = new StringBuilder(TAGS_ROOT).append(getParam(REQ_PARAM_TAG_ID))
				.append(getParam(REQ_PARAM_TAG_NAME, "")).append(STORIES).toString();

		logger.debug("tagStoriesPath {}", tagStoriesPath);
		cb.addRule(
				Join.path(tagStoriesPath).to(new StringBuilder(PUB).append(STORY).append(TAG_STORIES_PAGE).toString()));

		// storyNameStoriesPath
		final String storyNameStoriesPath = new StringBuilder(STORY_NAME_ROOT).append(getParam(REQ_PARAM_STORY_NAME_ID))
				.append(getParam(REQ_PARAM_STORY_NAME_URI, "")).append(STORIES).toString();
		logger.debug("storyNameStoriesPath {}", storyNameStoriesPath);
		cb.addRule(Join.path(storyNameStoriesPath)
				.to(new StringBuilder(PUB).append(STORY).append(STORY_NAME_STORIES_PAGE).toString()));

		return cb;
	}

	@Override
	public int priority() {
		return 20;
	}

	/**
	 * Utility method to help prepare OCPSoft Rewrite parameter String with forward
	 * slash at the end.
	 *
	 * @see <a href=
	 *      "https://github.com/ocpsoft/rewrite/blob/master/documentation/src/main/
	 *      asciidoc/configuration/parameters.asciidoc}">OCPSoft Parameterize
	 *      Rules<a>
	 */
	private String getParam(String reqParamName) {
		return getParam(reqParamName, FORWARD_SLASH);
	}

	/**
	 * Utility method to help prepare OCPSoft Rewrite parameter String with
	 * postAppend String attached at the end.
	 *
	 * @see <a href=
	 *      "https://github.com/ocpsoft/rewrite/blob/master/documentation/src/main/
	 *      asciidoc/configuration/parameters.asciidoc}">OCPSoft Parameterize
	 *      Rules<a>
	 */
	private String getParam(String reqParamName, String postAppend) {
		return "{" + reqParamName + "}" + postAppend;
	}

	/**
	 * For testing pages only
	 *
	 * @param cb
	 */
	private void testPages(ConfigurationBuilder cb) {
		// For Testing Disqus Only
		cb.addRule(Join.path(TEST_DISQUS_BOOTSTRAP_337_WITH_JSF_TEMPLATE).to(new StringBuilder(PUB).append(TEST)
				.append(TEST_DISQUS_BOOTSTRAP_337_WITH_JSF_TEMPLATE_PAGE).toString()));
		cb.addRule(Join.path(TEST_DISQUS_BOOTSTRAP_413_WITH_JSF_TEMPLATE).to(new StringBuilder(PUB).append(TEST)
				.append(TEST_DISQUS_BOOTSTRAP_413_WITH_JSF_TEMPLATE_PAGE).toString()));

		cb.addRule(Join.path(TEST_DISQUS_BOOTSTRAP_337_WITHOUT_JSF_TEMPLATE).to(new StringBuilder(PUB).append(TEST)
				.append(TEST_DISQUS_BOOTSTRAP_337_WITHOUT_JSF_TEMPLATE_PAGE).toString()));
		cb.addRule(Join.path(TEST_DISQUS_BOOTSTRAP_413_WITHOUT_JSF_TEMPLATE).to(new StringBuilder(PUB).append(TEST)
				.append(TEST_DISQUS_BOOTSTRAP_413_WITHOUT_JSF_TEMPLATE_PAGE).toString()));

		// For Testing Amazon Ads Only
		cb.addRule(Join.path(TEST_AMAZON_ADS)
				.to(new StringBuilder(PUB).append(TEST).append(TEST_AMAZON_ADS_PAGE).toString()));

		// For Testing Amazon Ads Only
		cb.addRule(Join.path(TEST_AMAZON_ADS_JSF)
				.to(new StringBuilder(PUB).append(TEST).append(TEST_AMAZON_ADS_JSF_PAGE).toString()));

		// For Testing Google Ads Only
		cb.addRule(Join.path(TEST_GOOGLE_ADS)
				.to(new StringBuilder(PUB).append(TEST).append(TEST_GOOGLE_ADS_PAGE).toString()));
	}
}

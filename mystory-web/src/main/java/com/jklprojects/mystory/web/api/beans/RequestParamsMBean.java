/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.api.beans;

import com.jklprojects.mystory.web.RequestParamNames;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * A Bean to access pre-defined request parameter names. Getter methods provides
 * the value of the request parameter.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 */
@Named("requestParamsMBean")
@ApplicationScoped
public class RequestParamsMBean implements RequestParamNames {

	public String getREQ_PARAM_AUTHOR_URL() {
		return REQ_PARAM_AUTHOR_URL;
	}

	public String getREQ_PARAM_CONTACTUS_ID() {
		return REQ_PARAM_CONTACTUS_ID;
	}

	public String getREQ_PARAM_CREATED_MONTH() {
		return REQ_PARAM_CREATED_MONTH;
	}

	public String getREQ_PARAM_CREATED_YEAR() {
		return REQ_PARAM_CREATED_YEAR;
	}

	public String getREQ_PARAM_REPLY_ID() {
		return REQ_PARAM_REPLY_ID;
	}

	public String getREQ_PARAM_STORY_ID() {
		return REQ_PARAM_STORY_ID;
	}

	public String getREQ_PARAM_STORY_NAME() {
		return REQ_PARAM_STORY_NAME;
	}

	public String getREQ_PARAM_STORY_NAME_ID() {
		return REQ_PARAM_STORY_NAME_ID;
	}

	public String getREQ_PARAM_STORY_NAME_URI() {
		return REQ_PARAM_STORY_NAME_URI;
	}

	public String getREQ_PARAM_STORY_TITLE() {
		return REQ_PARAM_STORY_TITLE;
	}

	public String getREQ_PARAM_TAG_ID() {
		return REQ_PARAM_TAG_ID;
	}

	public String getREQ_PARAM_TAG_NAME() {
		return REQ_PARAM_TAG_NAME;
	}

	public String getREQ_PARAM_USER_NAME() {
		return REQ_PARAM_USER_NAME;
	}

	public String getREQ_PARAM_USER_UID() {
		return REQ_PARAM_USER_UID;
	}

	public String getREQ_PARAM_USER_ROLE_TYPE_ID() {
		return REQ_PARAM_USER_ROLE_TYPE_ID;
	}
}

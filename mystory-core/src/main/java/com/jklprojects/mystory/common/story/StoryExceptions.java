/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

import com.jklprojects.mystory.common.ex.AppCodeException;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-05-03
 */
public enum StoryExceptions {
	STORY_001("STORY_001", "A Story exists with Story Name {0} and Post Type {1}"), STORY_002("STORY_002",
			"A Story with id {0} was not found"), STORY_003("STORY_003",
					"A Story Name with id {0} was not found"), STORY_004("STORY_004",
							"Stories with tag id {0} were not found"), STORY_005("STORY_005",
									"Stories with Story Name: {0} were not found");

	private String errorCode;
	private final String defaultMsg;

	StoryExceptions(String errorCode, String defaultMsg) {
		this.errorCode = errorCode;
		this.defaultMsg = defaultMsg;
	}

	public String getDefaultMsg() {
		return defaultMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public AppCodeException newAppCodeException() {
		return new AppCodeException(getErrorCode(), getDefaultMsg());
	}

	public AppCodeException newAppCodeException(Object... params) {
		return new AppCodeException(getErrorCode(), getDefaultMsg()).setMessageParams(params);
	}
}

/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.converter;

import com.jklprojects.mystory.common.story.StoryStatus;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 13, 2015
 */
@FacesConverter("storyStatusConverter")
public class StoryStatusConverter extends EnumConverter {
	public StoryStatusConverter() {
		super(StoryStatus.class);
	}
}

/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.converter;

import com.jklprojects.mystory.common.story.StoryPostType;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Apr 29, 2015 6:25:26 PM
 */
@FacesConverter("storyTypeConverter")
public class StoryPostTypeConverter extends EnumConverter {
	public StoryPostTypeConverter() {
		super(StoryPostType.class);
	}
}

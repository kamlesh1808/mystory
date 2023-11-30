/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.converter;

import com.jklprojects.mystory.common.story.StoryAccessType;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, June 9, 2015
 */
@FacesConverter("storyAccessTypeConverter")
public class StoryAccessTypeConverter extends EnumConverter {
	public StoryAccessTypeConverter() {
		super(StoryAccessType.class);
	}
}

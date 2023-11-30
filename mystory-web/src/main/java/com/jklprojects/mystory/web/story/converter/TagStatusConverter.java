/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.converter;

import com.jklprojects.mystory.common.story.TagStatus;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 16, 2015
 */
@FacesConverter("tagStatusConverter")
public class TagStatusConverter extends EnumConverter {

	public TagStatusConverter() {
		super(TagStatus.class);
	}
}

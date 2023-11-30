/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity.converter;

import com.jklprojects.mystory.common.story.StoryPostType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 7, 2015
 * @version 3, 2106-DEC-08
 */
@Converter
public class StoryPostTypeAttrConverter implements AttributeConverter<StoryPostType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(StoryPostType attribute) {
		return attribute.getId();
	}

	@Override
	public StoryPostType convertToEntityAttribute(Integer dbData) {
		if (dbData != null) {
			return StoryPostType.toEnum(dbData);
		}
		return StoryPostType.GENERAL_POST;
	}
}

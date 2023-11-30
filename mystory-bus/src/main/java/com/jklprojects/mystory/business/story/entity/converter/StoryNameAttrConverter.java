/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity.converter;

import com.jklprojects.mystory.common.story.StoryName;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 7, 2015
 */
@Converter()
public class StoryNameAttrConverter implements AttributeConverter<StoryName, Integer> {

	@Override
	public Integer convertToDatabaseColumn(StoryName attribute) {
		return attribute.getId();
	}

	@Override
	public StoryName convertToEntityAttribute(Integer dbData) {
		return StoryName.toEnum(dbData);
	}
}

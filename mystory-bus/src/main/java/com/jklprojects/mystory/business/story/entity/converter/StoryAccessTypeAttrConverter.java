/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity.converter;

import com.jklprojects.mystory.common.story.StoryAccessType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, June 9, 2015
 */
@Converter
public class StoryAccessTypeAttrConverter implements AttributeConverter<StoryAccessType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(StoryAccessType attribute) {
		return attribute.getId();
	}

	@Override
	public StoryAccessType convertToEntityAttribute(Integer dbData) {
		return StoryAccessType.toEnum(dbData);
	}
}

/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity.converter;

import com.jklprojects.mystory.common.story.StoryStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 7, 2015
 */
@Converter
public class StoryStatusAttrConverter implements AttributeConverter<StoryStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(StoryStatus attribute) {
		return attribute.getId();
	}

	@Override
	public StoryStatus convertToEntityAttribute(Integer dbData) {
		return StoryStatus.toEnum(dbData);
	}
}
